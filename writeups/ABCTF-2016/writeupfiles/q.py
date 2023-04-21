import itertools
import sys

class QSet:
    def __init__(self, input=None):
        self.qset = {}

        if input:
            for item in input:
                self.add_item(item)

    def add_item(self, item, amt=1):
        if item in self.qset:
            self.qset[item]+=amt
            if self.qset[item]==0:
                del self.qset[item]
        else:
            self.qset[item]=amt

    def add_qset(self, aqset):
        for item in aqset.qset:
            self.add_item(item, aqset.qset[item])

    def remove_qset(self, aqset):
        for item in aqset.qset:
            self.add_item(item, -aqset.qset[item])

    def contains_qset(self, aqset):
        for item in aqset.qset:
            if item in self.qset:
                if self.qset[item]<aqset.qset[item]:
                    return False
            else:
                return False
        return True

    def is_empty(self):
        return len(self.qset)==0

    def __str__(self):
        return str(self.qset)


def parse(z):
    steps = z.strip()
    steps = steps.split(',')
    reps = []
    for step in steps:
        # two / separated arguments
        rep = [y.strip() for y in step.split('/')]
        assert len(rep) == 2

        reps.append(rep)
    return [
        [
            QSet([] if x.split(' ')==[''] else x.split(' '))
            for x in vals
        ]
        for vals in reps
    ]


def interpret_step(program, qset, debug=False):
    if sum([y for (x, y) in qset.qset.items()]) != 6:
        print program, qset.qset
        sys.exit()

    if debug:
        print 'State', qset.qset
        print 'Internal State ', qset.qset, sum([y for (x, y) in qset.qset.items()])
    for process in program:
        if debug:
            print 'Exec', process[0].qset, process[1].qset
        if qset.contains_qset(process[1]):
            qset.remove_qset(process[1])
            qset.add_qset(process[0])
            return False
    return True


def interpret(program, inputs, maxsteps=100000, debug=False):
    program = parse(program)
    qset = QSet()

    for idx, val in enumerate(inputs):
        if type(val) in (int, long):
            qset.add_item('i%i' % idx, val)
        else:
            raise Exception("Invalid input type")

    step = 0
    while True:
        if step > maxsteps:
            raise Exception("Too many execution steps!")
        ret = interpret_step(program, qset, debug=debug)
        if ret:
            break
        step += 1

    success = True
    outputs = {}
    for key in qset.qset:
        if len(key)>1:
            if key[0]=="o":
                idx = key[1:]
                idx = int(idx)
                outputs[idx] = qset.qset[key]
            else:
                success = False
        else:
            success = False
    if success:
        outputs_arr = []
        for x in range(len(outputs)):
            if x in outputs:
                outputs_arr.append(outputs[x])
            else:
                raise Exception("Invalid output!")
        return outputs_arr
    else:
        raise Exception("Invalid output!")


def valid(program):
    return True
    steps = program.split(',')

    hit_o0 = False
    hit_o1 = False
    hit_o2 = False

    for step in steps:
        if step.startswith('o0'):
            hit_o0 = True
        if step.startswith('o1'):
            hit_o1 = True
        if step.startswith('o2'):
            hit_o2 = True

    if not hit_o0:
        return False

    if hit_o1:
        if not hit_o0:
            return False

    if hit_o2:
        if not hit_o1:
            return False

    # Must access
    if '/i0' not in program:
        return False

    # Must access
    if '/i1' not in program:
        return False

    # This is a simple loop
    if 'i0/i1,i1/i0' in program:
        return False

    return True


if __name__ == '__main__':

    def gen_var():
        for j in ('i', 'o'):
            for k in range(5):
                yield '%s%s' % (j, k)

    def gen_vars():
        for a in gen_var():
            for b in gen_var():
                # if a != b:
                    yield '%s/%s' % (a, b)

    prog_steps = list(gen_vars())

    idx = 0;
    count_valid = 0
    count_invalid = 0
    count_maxsteps = 0

    LOG_EVERY = 1000
    MAX_STEPS = 1000

    for i in range(1,6):
        print 'Program length ', i
        for step_sets in  itertools.product(prog_steps, repeat=i):
            program = ','.join(step_sets)
            if not valid(program):
                count_invalid += 1
                continue
            else:
                count_valid += 1

            idx += 1
            if idx % LOG_EVERY == 0:
                tot = count_valid + count_invalid
                print '(%s/%s=%s, %s/%s=%s) %s ' % (
                    count_valid, tot,
                    float(count_valid)/float(tot),
                    count_maxsteps, tot,
                    float(count_maxsteps) / tot,
                    program,
                ),

            try:
                result = interpret(program, [4, 2], maxsteps=MAX_STEPS, debug=False)
                if idx % LOG_EVERY == 0:
                    print result

                if result not in ([2, 4], [4, 2], [6], [4], [2]):
                    print result, program
                    sys.exit()

                if result[0] == 8:
                    print '>>>>>', program, '<<<<<'
                    print interpret(program, [4, 2], maxsteps=MAX_STEPS, debug=True)
                    sys.exit()
            except Exception as e:
                if 'Too many' in str(e):
                    count_maxsteps += 1
                if idx % LOG_EVERY == 0:
                    print
                pass
        # program = ','.join()
