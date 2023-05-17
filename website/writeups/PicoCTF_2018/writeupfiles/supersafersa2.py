import gmpy2


def isqrt(n):
    '''
    Calculates the integer square root
    for arbitrary large nonnegative integers
    '''
    if n < 0:
        raise ValueError('square root not defined for negative numbers')

    if n == 0:
        return 0
    a, b = divmod(bitlength(n), 2)
    x = 2**(a+b)
    while True:
        y = (x + n//x)//2
        if y >= x:
            return x
        x = y


def is_perfect_square(n):
    '''
    If n is a perfect square it returns sqrt(n),

    otherwise returns -1
    '''
    h = n & 0xF  # last hexadecimal "digit"

    if h > 9:
        return -1  # return immediately in 6 cases out of 16.

    # Take advantage of Boolean short-circuit evaluation
    if (h != 2 and h != 3 and h != 5 and h != 6 and h != 7 and h != 8):
        # take square root if you must
        t = isqrt(n)
        if t*t == n:
            return t
        else:
            return -1

    return -1


def rational_to_contfrac(x, y):
    '''
    Converts a rational x/y fraction into
    a list of partial quotients [a0, ..., an]
    '''
    a = x//y
    pquotients = [a]
    while a * y != x:
        x, y = y, x-a*y
        a = x//y
        pquotients.append(a)
    return pquotients


def convergents_from_contfrac(frac):
    '''
    computes the list of convergents
    using the list of partial quotients
    '''
    convs = []
    for i in range(len(frac)):
        convs.append(contfrac_to_rational(frac[0:i]))
    return convs


def contfrac_to_rational(frac):
    '''Converts a finite continued fraction [a0, ..., an]
     to an x/y rational.
     '''
    if len(frac) == 0:
        return (0, 1)
    num = frac[-1]
    denom = 1
    for _ in range(-2, -len(frac)-1, -1):
        num, denom = frac[_]*num+denom, num
    return (num, denom)


def bitlength(x):
    '''
    Calculates the bitlength of x
    '''
    assert x >= 0
    n = 0
    while x > 0:
        n = n+1
        x = x >> 1
    return n


def hack_RSA(e, n):
    '''
    Finds d knowing (e,n)
    applying the Wiener continued fraction attack
    '''
    frac = rational_to_contfrac(e, n)
    convergents = convergents_from_contfrac(frac)

    for (k, d) in convergents:

        # check if d is actually the key
        if k != 0 and (e*d-1) % k == 0:
            phi = (e*d-1)//k
            s = n - phi + 1
            # check if the equation x^2 - s*x + n = 0
            # has integer roots
            discr = s*s - 4*n
            if(discr >= 0):
                t = is_perfect_square(discr)
                if t != -1 and (s+t)%2 == 0:
                    print("Hacked!")
                    print(d)
                    return d


if __name__ == "__main__":
    n = 62521990254455432739739098327679950602675797333442676086848188780355806233664586212815747058830242509072941471918606448376223071702950597243409185734520501054880754651705541198856749099694484071501292887111220558418712756088261843103763153296918795811242273836779338023623847026392962143077450363442487934689
    e = 11376394003149076634899198125602078382240701942434104587266195449986160326784109595920286001442706286840942170874916183177235151594636401912319064648735169214636981775404170094629988884687077578483030764227613917415801022670556750159920041801902797676162135948461328656879638396798506273263833645665345860373
    c = 25768046345727464502597520221366212769169471825365967634630931748647232910338751793422711728554855006367814407810621525958992286707789113053995635463407498955539964935155035496760107228272520704862671855993478994453136635235568232731879221074380473331175949428476338755714015215366964946019773206537518303256

    d = hack_RSA(e, n)
    pt = gmpy2.powmod(c, d, n)
    print(pt)
    print("".join([chr((pt >> j) & 0xff) for j in reversed(range(0, 1000 << 3, 8))]))
