#!/usr/bin/env python

'''
You should install psyco and gmpy if you want maximal speed.

Filename: pyecm
Authors: Eric Larson <elarson3@uoregon.edu>, Martin Kelly <martin@martingkelly.com>,
License: GNU GPL (see <http://www.gnu.org/licenses/gpl.html> for more information.
Description: Factors a number using the Elliptic Curve Method, a fast algorithm for numbers < 50 digits.

We are using curves in Suyama's parametrization, but points are in affine coordinates, and the curve is in Wierstrass form.
The idea is to do many curves in parallel to take advantage of batch inversion algorithms. This gives asymptotically 7 modular multiplications per bit.

WARNING: pyecm is NOT a general-purpose number theory or elliptic curve library. Many of the functions have confusing calling syntax, and some will rather unforgivingly crash or return bad output if the input is not formatted exactly correctly. That said, there are a couple of functions that you CAN safely import into another program. These are: factors, isprime. However, be sure to read the documentation for each function that you use.
'''

import math
import sys
import random

try:
   import psyco
   psyco.full()
   PSYCO_EXISTS = True
except ImportError:
   PSYCO_EXISTS = False

try: # Try to use gmpy
   from gmpy2 import isqrt as sqrt
   from gmpy2 import iroot as root
   from gmpy2 import gcd, invert, mpz, next_prime
   import gmpy2
   GMPY_EXISTS = True
except ImportError:
   try:
      from gmpy import gcd, invert, mpz, next_prime, sqrt, root
      GMPY_EXISTS = True
   except ImportError:
      GMPY_EXISTS = False

if not GMPY_EXISTS:
   PRIMES = (5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 167)
   GMPY_EXISTS = False

   def gcd(a, b):
      '''Computes the Greatest Common Divisor of a and b using the standard quadratic time improvement to the Euclidean Algorithm.

   Returns the GCD of a and b.'''
      if b == 0:
         return a
      elif a == 0:
         return b

      count = 0

      if a < 0:
         a = -a
      if b < 0:
         b = -b

      while not ((a & 1) | (b & 1)):
         count += 1
         a >>= 1
         b >>= 1

      while not a & 1:
         a >>= 1

      while not b & 1:
         b >>= 1

      if b > a:
         b,a = a,b

      while b != 0 and a != b:
         a -= b
         while not (a & 1):
            a >>= 1

         if b > a:
            b, a = a, b

      return a << count

   def invert(a, b):
      '''Computes the inverse of a modulo b. b must be odd.

Returns the inverse of a (mod b).'''
      if a == 0 or b == 0:
         return 0

      truth = False
      if a < 0:
         truth = True
         a = -a

      b_orig = b
      alpha = 1
      beta = 0

      while not a & 1:
         if alpha & 1:
            alpha += b_orig
         alpha >>= 1
         a >>= 1

      if b > a:
         a, b = b, a
         alpha, beta = beta, alpha

      while b != 0 and a != b:
         a -= b
         alpha -= beta

         while not a & 1:
            if alpha & 1:
               alpha += b_orig
            alpha >>= 1
            a >>= 1

         if b > a:
            a,b = b,a
            alpha, beta = beta, alpha

      if a == b:
         a -= b
         alpha -= beta
         a, b = b, a
         alpha, beta = beta, alpha

      if a != 1:
         return 0

      if truth:
         alpha = b_orig - alpha

      return alpha

   def next_prime(n):
      '''Finds the next prime after n.

Returns the next prime after n.'''
      n += 1
      if n <= 167:
         if n <= 23:
            if n <= 3:
               return 3 - (n <= 2)
            n += (n & 1) ^ 1
            return n + (((4 - (n % 3)) >> 1) & 2)

         n += (n & 1) ^ 1
         inc = n % 3
         n += ((4 - inc) >> 1) & 2
         inc = 6 - ((inc + ((2 - inc) & 2)) << 1)

         while 0 in (n % 5, n % 7, n % 11):
            n += inc
            inc = 6 - inc
         return n

      n += (n & 1) ^ 1
      inc = n % 3
      n += ((4 - inc) >> 1) & 2
      inc = 6 - ((inc + ((2 - inc) & 2)) << 1)
      should_break = False

      while 1:
         for prime in PRIMES:
            if not n % prime:
               should_break = True
               break

         if should_break:
            should_break = False
            n += inc
            inc = 6 - inc
            continue

         p = 1
         for i in xrange(int(math.log(n) / LOG_2), 0, -1):
            p <<= (n >> i) & 1
            p = (p * p) % n

         if p == 1:
            return n

         n += inc
         inc = 6 - inc

   def mpz(n):
      '''A dummy function to ensure compatibility with those that do not have gmpy.

Returns n.'''
      return n

   def root(n, k):
      '''Finds the floor of the kth root of n. This is a duplicate of gmpy's root function.

Returns a tuple. The first item is the floor of the kth root of n. The second is 1 if the root is exact (as in, sqrt(16)) and 0 if it is not.'''
      low = 0
      high = n + 1
      while high > low + 1:
         mid = (low + high) >> 1
         mr = mid**k
         if mr == n:
            return (mid, 1)
         if mr < n:
            low = mid
         if mr > n:
            high = mid
      return (low, 0)

   def sqrt(n):
      return root(n, 2)[0]

# We're done importing. Now for some constants.
if GMPY_EXISTS:
   INV_C = 1.4
else:
   if PSYCO_EXISTS:
      INV_C = 7.3
   else:
      INV_C = 13.0
LOG_2 = math.log(2)
LOG_4 = math.log(4)
LOG_3_MINUS_LOG_LOG_2 = math.log(3) - math.log(LOG_2)
LOG_4_OVER_9 = LOG_4 / 9
_3_OVER_LOG_2 = 3 / LOG_2
_5_LOG_10 = 5 * math.log(10)
_7_OVER_LOG_2 = 7 / LOG_2
BIG = 2.0**512
BILLION = 10**9 # Something big that fits into an int.
MULT = math.log(3) / LOG_2
ONE = mpz(1)
SMALL = 2.0**(-30)
SMALLEST_COUNTEREXAMPLE_FASTPRIME = 2047
T = (type(mpz(1)), type(1), type(1L))
DUMMY = 'dummy' # Dummy value throughout the program
VERSION = '2.0.2'
_12_LOG_2_OVER_49 = 12 * math.log(2) / 49
RECORD = 1162795072109807846655696105569042240239

class ts:
   '''Does basic manipulations with Taylor Series (centered at 0). An example call to ts:
a = ts(7, 23, [1<<23, 2<<23, 3<<23]) -- now, a represents 1 + 2x + 3x^2. Here, computations will be done to degree 7, with accuracy 2^(-23). Input coefficients must be integers.'''

   def __init__(self, degree, acc, p):
      self.acc = acc
      self.coefficients = p[:degree + 1]
      while len(self.coefficients) <= degree:
         self.coefficients.append(0)

   def add(self, a, b):
      '''Adds a and b'''
      b_ = b.coefficients[:]
      a_ = a.coefficients[:]
      self.coefficients = []

      while len(b_) > len(a_):
         a_.append(0)
      while len(b_) < len(a_):
         b_.append(0)

      for i in xrange(len(a_)):
         self.coefficients.append(a_[i] + b_[i])

      self.acc = a.acc

   def ev(self, x):
      '''Returns a(x)'''
      answer = 0
      for i in xrange(len(self.coefficients) - 1, -1, -1):
         answer *= x
         answer += self.coefficients[i]
      return answer

   def evh(self):
      '''Returns a(1/2)'''
      answer = 0
      for i in xrange(len(self.coefficients) - 1, -1, -1):
         answer >>= 1
         answer += self.coefficients[i]
      return answer

   def evmh(self):
      '''Returns a(-1/2)'''
      answer = 0
      for i in xrange(len(self.coefficients) - 1, -1, -1):
         answer = - answer >> 1
         answer += self.coefficients[i]
      return answer

   def int(self):
      '''Replaces a by an integral of a'''
      self.coefficients = [0] + self.coefficients
      for i in xrange(1, len(self.coefficients)):
         self.coefficients[i] /= i

   def lindiv(self, a):
      '''a.lindiv(k) -- sets a/(x-k/2) for integer k'''
      for i in xrange(len(self.coefficients) - 1):
         self.coefficients[i] <<= 1
         self.coefficients[i] /= a
         self.coefficients[i + 1] -= self.coefficients[i]
      self.coefficients[-1] <<= 1
      self.coefficients[-1] /= a

   def neg(self):
      '''Sets a to -a'''
      for i in xrange(len(self.coefficients)):
         self.coefficients[i] = - self.coefficients[i]

   def set(self, a):
      '''a.set(b) sets a to b'''
      self.coefficients = a.coefficients[:]
      self.acc = a.acc

   def simp(self):
      '''Turns a into a type of Taylor series that can be fed into ev, but cannot be computed with further.'''
      for i in xrange(len(self.coefficients)):
         shift = max(0, int(math.log(abs(self.coefficients[i]) + 1) / LOG_2) - 1000)
         self.coefficients[i] = float(self.coefficients[i] >> shift)
         shift = self.acc - shift
         for _ in xrange(shift >> 9):
            self.coefficients[i] /= BIG
         self.coefficients[i] /= 2.0**(shift & 511)
         if abs(self.coefficients[i] / self.coefficients[0]) <= SMALL:
            self.coefficients = self.coefficients[:i]
            break

# Functions are declared in alphabetical order except when dependencies force them to be at the end.

def add(p1, p2,  n):
   '''Adds first argument to second (second argument is not preserved). The arguments are points on an elliptic curve. The first argument may be a tuple instead of a list. The addition is thus done pointwise. This function has bizzare input/output because there are fast algorithms for inverting a bunch of numbers at once.

Returns a list of the addition results.'''
   inv = range(len(p1))

   for i in xrange(len(p1)):
      inv[i] = p1[i][0] - p2[i][0]

   inv = parallel_invert(inv, n)

   if not isinstance(inv, list):
      return inv

   for i in xrange(len(p1)):
      m = ((p1[i][1] - p2[i][1]) * inv[i]) % n
      p2[i][0] = (m * m - p1[i][0] - p2[i][0]) % n
      p2[i][1] = (m * (p1[i][0] - p2[i][0]) - p1[i][1]) % n

   return p2

def add_sub_x_only(p1, p2,  n):
   '''Given a pair of lists of points p1 and p2, computes the x-coordinates of
p1[i] + p2[i] and p1[i] - p2[i] for each i.

Returns two lists, the first being the sums and the second the differences.'''
   sums = range(len(p1))
   difs = range(len(p1))

   for i in xrange(len(p1)):
      sums[i] = p2[i][0] - p1[i][0]

   sums = parallel_invert(sums, n)

   if not isinstance(sums, list):
      return (sums, None)

   for i in xrange(len(p1)):
      ms = ((p2[i][1] - p1[i][1]) * sums[i]) % n
      md = ((p2[i][1] + p1[i][1]) * sums[i]) % n
      sums[i] = (ms * ms - p1[i][0] - p2[i][0]) % n
      difs[i] = (md * md - p1[i][0] - p2[i][0]) % n

   sums = tuple(sums)
   difs = tuple(difs)

   return (sums, difs)

def atdn(a, d, n):
   '''Calculates a to the dth power modulo n.

Returns the calculation's result.'''
   x = 1
   pos = int(math.log(d) / LOG_2)

   while pos >= 0:
      x = (x * x) % n
      if (d >> pos) & 1:
         x *= a
      pos -= 1

   return x % n

def copy(p):
   '''Copies a list using only deep copies.

Returns a copy of p.'''
   answer = []
   for i in p:
      answer.append(i[:])

   return answer

def could_be_prime(n):
   '''Performs some trials to compute whether n could be prime. Run time is O(N^3 / (log N)^2) for N bits.

Returns whether it is possible for n to be prime (True or False).
'''
   if n < 2:
      return False
   if n == 2:
      return True
   if not n & 1:
      return False

   product = ONE
   log_n = int(math.log(n)) + 1
   bound = int(math.log(n) / (LOG_2 * math.log(math.log(n))**2)) + 1
   if bound * log_n >= n:
      bound = 1
      log_n = int(sqrt(n))
   prime_bound = 0
   prime = 3

   for _ in xrange(bound):
      p = []
      prime_bound += log_n
      while prime <= prime_bound:
         p.append(prime)
         prime = next_prime(prime)
      if p != []:
         p = prod(p)
         product = (product * p) % n

   return gcd(n, product) == 1

def double(p, n):
   '''Doubles each point in the input list. Much like the add function, we take advantage of fast inversion.

Returns the doubled list.'''
   inv = range(len(p))

   for i in xrange(len(p)):
      inv[i] = p[i][1] << 1

   inv = parallel_invert(inv, n)

   if not isinstance(inv, list):
      return inv

   for i in xrange(len(p)):
      x = p[i][0]
      m = (x * x) % n
      m = ((m + m + m + p[i][2]) * inv[i]) % n
      p[i][0] = (m * m - x - x) % n
      p[i][1] = (m * (x - p[i][0]) - p[i][1]) % n

   return p

def fastprime(n):
   '''Tests for primality of n using an algorithm that is very fast, O(N**3 / log(N)) (assuming quadratic multiplication) where n has N digits, but ocasionally inaccurate for n >= 2047.

Returns the primality of n (True or False).'''
   if not could_be_prime(n):
      return False
   if n == 2:
      return True

   j = 1
   d = n >> 1

   while not d & 1:
      d >>= 1
      j += 1

   p = 1
   pos = int(math.log(d) / LOG_2)

   while pos >= 0:
      p = (p * p) % n
      p <<= (d >> pos) & 1
      pos -= 1

   if p in (n - 1, n + 1):
      return True

   for _ in xrange(j):
      p = (p * p) % n

      if p == 1:
         return False
      elif p == n - 1:
         return True

   return False

def greatest_n(phi_max):
   '''Finds the greatest n such that phi(n) < phi_max.

Returns the greatest n such that phi(n) < phi_max.'''
   phi_product = 1
   product = 1
   prime = 1
   while phi_product <= phi_max:
      prime = next_prime(prime)
      phi_product *= prime - 1
      product *= prime

   n_max = (phi_max * product) / phi_product

   phi_values = range(n_max)

   prime = 2
   while prime <= n_max:
      for i in xrange(0, n_max, prime):
         phi_values[i] -= phi_values[i] / prime

      prime = next_prime(prime)

   for i in xrange(n_max - 1, 0, -1):
      if phi_values[i] <= phi_max:
         return i

def inv_const(n):
   '''Finds a constant relating the complexity of multiplication to that of modular inversion.

Returns the constant for a given n.'''
   return int(INV_C * math.log(n)**0.42)

def naf(d):
   '''Finds a number's non-adjacent form, reverses the bits, replaces the
-1's with 3's, and interprets the result base 4.

Returns the result interpreted as if in base 4.'''
   g = 0L
   while d:
      g <<= 2
      g ^= ((d & 2) & (d << 1)) ^ (d & 1)
      d += (d & 2) >> 1
      d >>= 1
   return g

def parallel_invert(l, n):
   '''Inverts all elements of a list modulo some number, using 3(n-1) modular multiplications and one inversion.

Returns the list with all elements inverted modulo 3(n-1).'''
   l_ = l[:]
   for i in xrange(len(l)-1):
      l[i+1] = (l[i] * l[i+1]) % n

   try:
      inv = invert(l[-1], n)
   except ZeroDivisionError:
      inv = 0
   if inv == 0:
      return gcd(l[-1], n)

   for i in xrange(len(l)-1, 0, -1):
      l[i] = (inv * l[i-1]) % n
      inv = (inv * l_[i]) % n
   l[0] = inv

   return l

def prod(p):
   '''Multiplies all elements of a list together. The order in which the
elements are multiplied is chosen to take advantage of Python's Karatsuba
Multiplication

Returns the product of everything in p.'''
   jump = 1

   while jump < len(p):
      for i in xrange(0, len(p) - jump, jump << 1):
         p[i] *= p[i + jump]
         p[i + jump] = None

      jump <<= 1

   return p[0]

def rho_ev(x, ts):
   '''Evaluates Dickman's rho function, which calculates the asymptotic
probability as N approaches infinity (for a given x) that all of N's factors
are bounded by N^(1/x).'''
   return ts[int(x)].ev(x - int(x) - 0.5)

def rho_ts(n):
   '''Makes a list of Taylor series for the rho function centered at 0.5, 1.5, 2.5 ... n + 0.5. The reason this is necessary is that the radius of convergence of rho is small, so we need lots of Taylor series centered at different places to correctly evaluate it.

Returns a list of Taylor series.'''
   f = ts(10, 10, [])
   answer = [ts(10, 10, [1])]
   for _ in xrange(n):
      answer.append(ts(10, 10, [1]))
   deg = 5
   acc = 50 + n * int(1 + math.log(1 + n) + math.log(math.log(3 + n)))
   r = 1
   rho_series = ts(1, 10, [0])
   while r != rho_series.coefficients[0]:
      deg = (deg + (deg << 2)) / 3
      r = rho_series.coefficients[0]
      rho_series = ts(deg, acc, [(1L) << acc])
      center = 0.5
      for i in xrange(1, n+1):
         f.set(rho_series)
         center += 1
         f.lindiv(int(2*center))
         f.int()
         f.neg()
         d = ts(deg, acc, [rho_series.evh() - f.evmh()])
         f.add(f, d)
         rho_series.set(f)
         f.simp()
         answer[i].set(f)
      rho_series.simp()

   return answer

def sub_sub_sure_factors(f, u, curve_parameter):
   '''Finds all factors that can be found using ECM with a smoothness bound of u and sigma and give curve parameters. If that fails, checks for being a prime power and does Fermat factoring as well.

Yields factors.'''
   while not (f & 1):
      yield 2
      f >>= 1

   while not (f % 3):
      yield 3
      f /= 3

   if isprime(f):
      yield f
      return

   log_u = math.log(u)
   u2 = int(_7_OVER_LOG_2 * u * log_u / math.log(log_u))
   primes = []
   still_a_chance = True
   log_mo = math.log(f + 1 + sqrt(f << 2))

   g = gcd(curve_parameter, f)
   if g not in (1, f):
      for factor in sub_sub_sure_factors(g, u, curve_parameter):
         yield factor
      for factor in sub_sub_sure_factors(f/g, u, curve_parameter):
         yield factor
      return

   g2 = gcd(curve_parameter**2 - 5, f)
   if g2 not in (1, f):
      for factor in sub_sub_sure_factors(g2, u, curve_parameter):
         yield factor
      for factor in sub_sub_sure_factors(f / g2, u, curve_parameter):
         yield factor
      return

   if f in (g, g2):
      yield f

   while still_a_chance:
      p1 = get_points([curve_parameter], f)
      for prime in primes:
         p1 = multiply(p1, prime, f)
         if not isinstance(p1, list):
            if p1 != f:
               for factor in sub_sub_sure_factors(p1, u, curve_parameter):
                  yield factor
               for factor in sub_sub_sure_factors(f/p1, u, curve_parameter):
                  yield factor
               return
            else:
               still_a_chance = False
               break

      if not still_a_chance:
         break

      prime = 1
      still_a_chance = False
      while prime < u2:
         prime = next_prime(prime)
         should_break = False
         for _ in xrange(int(log_mo / math.log(prime))):
            p1 = multiply(p1, prime, f)
            if not isinstance(p1, list):
               if p1 != f:
                  for factor in sub_sub_sure_factors(p1, u, curve_parameter):
                     yield factor
                  for factor in sub_sub_sure_factors(f/p1, u, curve_parameter):
                     yield factor
                  return

               else:
                  still_a_chance = True
                  primes.append(prime)
                  should_break = True
                  break
         if should_break:
            break

   for i in xrange(2, int(math.log(f) / LOG_2) + 2):
      r = root(f, i)
      if r[1]:
         for factor in sub_sub_sure_factors(r[0], u, curve_parameter):
            for _ in xrange(i):
               yield factor
         return

   a = 1 + sqrt(f)
   bsq = a * a - f
   iter = 0

   while bsq != sqrt(bsq)**2 and iter < 3:
      a += 1
      iter += 1
      bsq += a + a - 1

   if bsq == sqrt(bsq)**2:
      b = sqrt(bsq)
      for factor in sub_sub_sure_factors(a - b, u, curve_parameter):
         yield factor
      for factor in sub_sub_sure_factors(a + b, u, curve_parameter):
         yield factor
      return

   yield f
   return

def sub_sure_factors(f, u, curve_params):
   '''Factors n as far as possible using the fact that f came from a mainloop call.

Yields factors of n.'''
   if len(curve_params) == 1:
      for factor in sub_sub_sure_factors(f, u, curve_params[0]):
         yield factor
      return

   c1 = curve_params[:len(curve_params) >> 1]
   c2 = curve_params[len(curve_params) >> 1:]

   if mainloop(f, u, c1) == 1:
      for factor in sub_sure_factors(f, u, c2):
         yield factor
      return

   if mainloop(f, u, c2) == 1:
      for factor in sub_sure_factors(f, u, c1):
         yield factor
      return

   for factor in sub_sure_factors(f, u, c1):
      if isprime(factor):
         yield factor
      else:
         for factor_of_factor in sub_sure_factors(factor, u, c2):
            yield factor_of_factor

   return

def subtract(p1, p2,  n):
   '''Given two points on an elliptic curve, subtract them pointwise.

Returns the resulting point.'''
   inv = range(len(p1))

   for i in xrange(len(p1)):
      inv[i] = p2[i][0] - p1[i][0]

   inv = parallel_invert(inv, n)

   if not isinstance(inv, list):
      return inv

   for i in xrange(len(p1)):
      m = ((p1[i][1] + p2[i][1]) * inv[i]) % n
      p2[i][0] = (m * m - p1[i][0] - p2[i][0]) % n
      p2[i][1] = (m * (p1[i][0] - p2[i][0]) + p1[i][1]) % n

   return p2

def congrats(f, veb):
   '''Prints a congratulations message when a record factor is found. This only happens if the second parameter (verbosity) is set to True.

Returns nothing.'''

   if veb and f > RECORD:
      print 'Congratulations! You may have found a record factor via pyecm!'
      print 'Please email the Mainloop call to Eric Larson <elarson3@uoregon.edu>'

   return

def sure_factors(n, u, curve_params, veb, ra, ov, tdb, pr):
   '''Factor n as far as possible with given smoothness bound and curve parameters, including possibly (but very rarely) calling ecm again.

Yields factors of n.'''
   f = mainloop(n, u, curve_params)

   if f == 1:
      return

   if veb:
      print 'Found factor:', f
      print 'Mainloop call was:', n, u, curve_params

   if isprime(f):
      congrats(f, veb)
      yield f
      n /= f
      if isprime(n):
         yield n
      if veb:
         print '(factor processed)'
      return

   for factor in sub_sure_factors(f, u, curve_params):
      if isprime(factor):
         congrats(f, veb)
         yield factor
      else:
         if veb:
            print 'entering new ecm loop to deal with stubborn factor:', factor
         for factor_of_factor in ecm(factor, True, ov, veb, tdb, pr):
            yield factor_of_factor
      n /= factor

   if isprime(n):
      yield n

   if veb:
      print '(factor processed)'
   return

def to_tuple(p):
   '''Converts a list of two-element lists into a list of two-element tuples.

Returns a list.'''
   answer = []
   for i in p:
      answer.append((i[0], i[1]))

   return tuple(answer)

def mainloop(n, u, p1):
   ''' Input:   n  -- an integer to (try) to factor.
         u  -- the phase 1 smoothness bound
         p1 -- a list of sigma parameters to try

   Output:   A factor of n. (1 is returned on faliure).

   Notes:
      1. Other parameters, such as the phase 2 smoothness bound are selected by the mainloop function.
      2. This function uses batch algorithms, so if p1 is not long enough, there will be a loss in efficiency.
      3. Of course, if p1 is too long, then the mainloop will have to use more memory.
           [The memory is polynomial in the length of p1, log u, and log n].'''
   k = inv_const(n)
   log_u = math.log(u)
   log_log_u = math.log(log_u)
   log_n = math.log(n)
   u2 = int(_7_OVER_LOG_2 * u * log_u / log_log_u)
   ncurves = len(p1)
   w = int(math.sqrt(_3_OVER_LOG_2 * ncurves / k) - 0.5)
   number_of_primes = int((ncurves << w) * math.sqrt(LOG_4_OVER_9 * log_n / k) / log_u) # Lagrange multipliers!
   number_of_primes = min(number_of_primes, int((log_n / math.log(log_n))**2 * ncurves / log_u), int(u / log_u))
   number_of_primes = max(number_of_primes, 1)
   m = math.log(number_of_primes) + log_log_u
   w = min(w, int((m - 2 * math.log(m) + LOG_3_MINUS_LOG_LOG_2) / LOG_2))
   w = max(w, 1)
   max_order = n + sqrt(n << 2) + 1 # By Hasse's theorem.
   det_bound = ((1 << w) - 1 + ((w & 1) << 1)) / 3
   log_mo = math.log(max_order)
   p = range(number_of_primes)
   prime = mpz(2)

   p1 = get_points(p1, n)
   if not isinstance(p1, list):
      return p1

   for _ in xrange(int(log_mo / LOG_2)):
      p1 = double(p1, n)
      if not isinstance(p1, list):
         return p1

   for i in xrange(1, det_bound):
      prime  = (i << 1) + 1
      if isprime(prime):
         for _ in xrange(int(log_mo / math.log(prime))):
            p1 = multiply(p1, prime, n)
            if not isinstance(p1, list):
               return p1

   while prime < sqrt(u) and isinstance(p1, list):
      for i in xrange(number_of_primes):
         prime = next_prime(prime)
         p[i] = prime ** max(1, int(log_u / math.log(prime)))
      p1 = fast_multiply(p1, prod(p),  n, w)

   if not isinstance(p1, list):
      return p1

   while prime < u and isinstance(p1, list):
      for i in xrange(number_of_primes):
         prime = next_prime(prime)
         p[i] = prime
      p1 = fast_multiply(p1, prod(p),  n, w)

   if not isinstance(p1, list):
      return p1

   del p

   small_jump = int(greatest_n((1 << (w + 2)) / 3))
   small_jump = max(120, small_jump)
   big_jump = 1 + (int(sqrt((5 << w) / 21)) << 1)
   total_jump = small_jump * big_jump
   big_multiple = max(total_jump << 1, ((int(next_prime(prime)) - (total_jump >> 1)) / total_jump) * total_jump)
   big_jump_2 = big_jump >> 1
   small_jump_2 = small_jump >> 1
   product = ONE

   psmall_jump = multiply(p1, small_jump, n)
   if not isinstance(psmall_jump, list):
      return psmall_jump

   ptotal_jump = multiply(psmall_jump, big_jump, n)
   if not isinstance(ptotal_jump, list):
      return ptotal_jump

   pgiant_step = multiply(p1, big_multiple, n)
   if not isinstance(pgiant_step, list):
      return pgiant_step

   small_multiples = [None]
   for i in xrange(1, small_jump >> 1):
      if gcd(i, small_jump) == 1:
         tmp = multiply(p1, i, n)
         if not isinstance(tmp, list):
            return tmp
         for i in xrange(len(tmp)):
            tmp[i] = tmp[i][0]
         small_multiples.append(tuple(tmp))
      else:
         small_multiples.append(None)
   small_multiples = tuple(small_multiples)

   big_multiples = [None]
   for i in xrange(1, (big_jump + 1) >> 1):
      tmp = multiply(psmall_jump, i, n)
      if not isinstance(tmp, list):
         return tmp
      big_multiples.append(to_tuple(tmp))
   big_multiples = tuple(big_multiples)

   psmall_jump = to_tuple(psmall_jump)
   ptotal_jump = to_tuple(ptotal_jump)

   while big_multiple < u2:
      big_multiple += total_jump
      center_up = big_multiple
      center_down = big_multiple
      pgiant_step = add(ptotal_jump, pgiant_step, n)
      if not isinstance(pgiant_step, list):
         return pgiant_step

      prime_up = next_prime(big_multiple - small_jump_2)
      while prime_up < big_multiple + small_jump_2:
         s = small_multiples[abs(int(prime_up) - big_multiple)]
         for j in xrange(ncurves):
            product *= pgiant_step[j][0] - s[j]
            product %= n
         prime_up = next_prime(prime_up)

      for i in xrange(1, big_jump_2 + 1):
         center_up += small_jump
         center_down -= small_jump

         pmed_step_up, pmed_step_down = add_sub_x_only(big_multiples[i], pgiant_step, n)
         if pmed_step_down == None:
            return pmed_step_up

         while prime_up < center_up + small_jump_2:
            s = small_multiples[abs(int(prime_up) - center_up)]
            for j in xrange(ncurves):
               product *= pmed_step_up[j] - s[j]
               product %= n
            prime_up = next_prime(prime_up)

         prime_down = next_prime(center_down - small_jump_2)
         while prime_down < center_down + small_jump_2:
            s = small_multiples[abs(int(prime_down) - center_down)]
            for j in xrange(ncurves):
               product *= pmed_step_down[j] - s[j]
               product %= n
            prime_down = next_prime(prime_down)

   if gcd(product, n) != 1:
      return gcd(product, n)

   return 1

def fast_multiply(p, d, n, w):
   '''Multiplies each element of p by d. Multiplication is on
an elliptic curve. Both d and <p> must be odd. Also, <p> may not be divisible by anything less than or equal to 2 * (2**w + (-1)**w) / 3 + 1.

Returns the list p multiplied by d.'''

   mask = (1 << (w << 1)) - 1
   flop = mask / 3
   g = naf(d) >> 4
   precomp = {}
   m = copy(p)
   p = double(p, n)

   for i in xrange((flop >> w) + (w & 1)):
      key = naf((i << 1) + 1)
      precomp[key] = to_tuple(m)
      precomp[((key & flop) << 1) ^ key] = precomp[key]
      m = add(p, m, n)

   while g > 0:
      if g & 1:
         t = g & mask
         sh = 1 + int(math.log(t) / LOG_4)
         for _ in xrange(sh):
            p = double(p, n)

         if g & 2:
            p = subtract(precomp[t], p, n)
         else:
            p = add(precomp[t], p,  n)

         g >>= (sh << 1)
         if not isinstance(p, list):
            return p
      else:
         p = double(p, n)
         g >>= 2

   return p

def get_points(p1, n):
   '''Outputs points in Weierstrass form, given input in Suyama
parametrization.

Returns the points.'''
   p1 = list(p1)
   invs = p1[:]
   ncurves = len(p1)

   for j in xrange(ncurves):
      sigma = mpz(p1[j])
      u = (sigma**2 - 5) % n
      v = sigma << 2
      i = (((u * u) % n) * ((v * u << 2) % n)) % n
      p1[j] = [u, v, i]
      invs[j] = (i * v) % n

   invs = parallel_invert(invs, n)
   if not isinstance(invs, list):
      return invs

   for j in xrange(ncurves):
      u, v, i = p1[j]
      inv = invs[j]

      a = (((((((v - u)**3 % n) * v) % n) * (u + u + u + v)) % n) * inv - 2) % n # <-- This line is a thing of beauty
      x_0 = (((((u * i) % n) * inv) % n) ** 3) % n # And this one gets second place
      b = ((((x_0 + a) * x_0 + 1) % n) * x_0) % n
      x_0 = (b * x_0) % n
      y_0 = (b**2) % n

      while a % 3:
         a += n

      x_0 = (x_0 + a * b / 3) % n
      c = (y_0 * ((1 - a**2 / 3) % n)) % n

      p1[j] = [x_0, y_0, c]

   return p1

def isprime(n):
   ''' Tests for primality of n trying first fastprime and then a slower but accurate algorithm. Time complexity is O(N**3) (assuming quadratic multiplication), where n has N digits.

Returns the primality of n (True or False).'''
   if not fastprime(n):
      return False
   elif n < SMALLEST_COUNTEREXAMPLE_FASTPRIME:
      return True

   do_loop = False
   j = 1
   d = n >> 1
   a = 2
   bound = int(0.75 * math.log(math.log(n)) * math.log(n)) + 1

   while not d & 1:
      d >>= 1
      j += 1

   while a < bound:
      a = next_prime(a)
      p = atdn(a, d, n)

      if p == 1 or p == n - 1:
         continue

      for _ in xrange(j):
         p = (p * p) % n

         if p == 1:
            return False
         elif p == n - 1:
            do_loop = True
            break

      if do_loop:
         do_loop = False
         continue

      return False

   return True

def multiply(p1, d, n):
   '''Multiplies each element of a list by a number, without using too much overhead.

Returns a list p multiplied through by d.'''
   pos = int(math.log(d) / LOG_2) - 1
   p = copy(p1)

   while pos >= 0:
      p = double(p, n)
      if not isinstance(p, list):
         return p
      if (d >> pos) & 1:
         p = add(p1, p,  n)
         if not isinstance(p, list):
            return p
      pos -= 1

   return p

def ecm(n, ra, ov, veb, tdb, pr): # DOCUMENTATION
   '''Input:
   n   -- An integer to factor
   veb -- If True, be verbose
   ra  -- If True, select sigma values randomly
   ov  -- How asymptotically fast the calculation is
   pr  -- What portion of the total processing power this run gets

Output: Factors of n, via a generator.

Notes:
1. A good value of ov for typical numbers is somewhere around 10. If this parameter is too high, overhead and memory usage grow.
2. If ra is set to False and veb is set to True, then results are reproducible. If ra is set to True, then one number may be done in parallel on disconnected machines (at only a small loss of efficiency, which is less if pr is set correctly).'''

   if veb:
      looking_for = 0
   k = inv_const(n)

   if ra:
      sigma = 6 + random.randrange(BILLION)
   else:
      sigma = 6

   for factor in sure_factors(n, k, range(sigma, sigma + k), veb, ra, ov, tdb, pr):
      yield factor
      n /= factor

   if n == 1:
      return

   if ra:
      sigma += k + random.randrange(BILLION)
   else:
      sigma += k

   x_max = 0.5 * math.log(n) / math.log(k)
   t = rho_ts(int(x_max))
   prime_probs = []
   nc = 1 + int(_12_LOG_2_OVER_49 * ov * ov * k)
   eff_nc = nc / pr

   for i in xrange(1 + (int(math.log(n)) >> 1)):
      if i < math.log(tdb):
         prime_probs.append(0)
      else:
         prime_probs.append(1.0/i)

   for i in xrange(len(prime_probs)):
      p_success = rho_ev((i - 2.65) / math.log(k), t)
      p_fail = max(0, (1 - p_success * math.log(math.log(k)))) ** (k / pr)
      prime_probs[i] = p_fail * prime_probs[i] / (p_fail * prime_probs[i] + 1 - prime_probs[i])

   while n != 1:
      low = int(k)
      high = n
      while high > low + 1:
         u = (high + low) >> 1
         sum = 0
         log_u = math.log(u)
         for i in xrange(len(prime_probs)):
            log_p = i - 2.65
            log_u = math.log(u)
            quot = log_p / log_u
            sum += prime_probs[i] * (rho_ev(quot - 1, t) - rho_ev(quot, t) * log_u)
         if sum < 0:
            high = u
         else:
            low = u

      if ra:
         sigma += nc + random.randrange(BILLION)
      else:
         sigma += nc

      for factor in sure_factors(n, u, range(sigma, sigma + nc), veb, ra, ov, tdb, pr):
         yield factor
         n /= factor

      for i in xrange(len(prime_probs)):
         p_success = rho_ev((i - 2.65) / math.log(u), t)
         p_fail = max(0, (1 - p_success * math.log(math.log(u)))) ** eff_nc
         prime_probs[i] = p_fail * prime_probs[i] / (p_fail * prime_probs[i] + 1 - prime_probs[i])
      prime_probs = prime_probs[:1 + (int(math.log(n)) >> 1)]

      if veb and n != 1:
         m = max(prime_probs)
         for i in xrange(len(prime_probs)):
            if prime_probs[i] == m:
               break

         new_looking_for = (int(i / _5_LOG_10) + 1)
         new_looking_for += new_looking_for << 2
         if new_looking_for != looking_for:
            looking_for = new_looking_for
            print 'Searching for primes around', looking_for, 'digits'

   return

def factors(n, veb, ra, ov, pr):
   '''Generates factors of n.
Strips small primes, then feeds to ecm function.

Input:
   n   -- An integer to factor
   veb -- If True, be verbose
   ra  -- If True, select sigma values randomly
   ov  -- How asymptotically fast the calculation is
   pr  -- What portion of the total processing power this run gets

Output: Factors of n, via a generator.

Notes:
1. A good value of ov for typical numbers is somewhere around 10. If this parameter is too high, overhead and memory usage grow.
2. If ra is set to False and veb is set to True, then results are reproducible. If ra is set to True, then one number may be done in parallel on disconnected machines (at only a small loss of efficiency, which is less if pr is set correctly).'''


   if type(n) not in T:
      raise ValueError, 'Number given must be integer or long.'

   if not 0 < pr <= 1:
      yield 'Error: pr must be between 0 and 1'
      return

   while not n & 1:
      n >>= 1
      yield 2

   n = mpz(n)
   k = inv_const(n)
   prime = 2
   trial_division_bound = max(10 * k**2, 100)

   while prime < trial_division_bound:
      prime = next_prime(prime)
      while not n % prime:
         n /= prime
         yield prime

   if isprime(n):
      yield n
      return

   if n == 1:
      return

   for factor in ecm(n, ra, ov, veb, trial_division_bound, pr):
      yield factor

### End of algorithm code; beginning of interface code ##

def is_switch(s):
   '''Tests whether the input string is a switch (e.g. "-v" or "--help").

Returns True or False.'''

   for i in xrange(len(s)):
      if s[i] != '-':
         break

   if i == 0: # s not begin with "-"
      return False
   for char in s[i:]:
      if not char.isalpha():
         if char == '=': # Switches like "--portion=" are acceptable
            return True
         else:
            return False
   return True

def parse_switch(s, switch):
   '''Parses a switch in the form '--string=num' and returns num or calls help() if the string is invalid.

Returns the num in '--string=num'.'''

   try:
      return float(s[len(switch) + 3:])
   except ValueError:
      help()

def valid_input(s):
   '''Tests the input string for validity as a mathematical expressions.

Returns True or False.'''
   valid = ('(', ')', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '*', '/', '^', ' ', '\t')

   for char in s:
      if char not in valid:
         return False

   return True

def help():
   print   '''\
Usage: pyecm [OPTION] [expression to factor]
Factor numbers using the Elliptic Curve Method.

   --portion=num    Does only part of the work for factoring, corresponding to
what fraction of the total work the machine is doing. Useful for working in
parallel. For example, if there are three machines: 1GHz, 1GHz, and 2GHz, print
should be set to 0.25 for the 1GHz machines and 0.5 for the 2GHz machine.
Implies -r and -v. -r is needed to avoid duplicating work and -v is needed to
report results.
   --ov=num        Sets the value of the internal parameter ov, which
determines the trade-off between memory and time usage. Do not touch if you do
not know what you are doing. Please read all the documentation and understand
the full implications of the parameter before using this switch.
   -n, --noverbose   Terse. On by default. Needed to cancel the -v from the
--portion or --random switches. If both -n and -v are specified, the one
specified last takes precedence.
   -r, --random     Chooses random values for sigma, an internal parameter in
the calculation. Implies -v; if you're doing something random, you want to know
what's happening.
   -v, --verbose    Explains what is being done with intermediate calculations
and results.

With no integers to factor given via command-line, read standard input.

Please report bugs to Eric Larson <elarson3@uoregon.edu>.'''
   sys.exit()

def command_line(veb, ra, ov, pr):
   l = len(sys.argv)
   for i in xrange(1, l):
      if not is_switch(sys.argv[i]):
         break

   for j in xrange(i, l): # Start with the first non-switch
      if j != i: # Pretty printing
         print
      response = sys.argv[j]
      if valid_input(response):
         response = response.replace('^', '**')
         try:
            n = eval(response)
            int(n)
         except (SyntaxError, TypeError, ValueError):
            help()
      else:
         help()

      print   'Factoring %d:' % n
      if n < 0:
         print   -1
         n = -n
      if n == 0:
         print   '0 does not have a well-defined factorization.'
         continue
      elif n == 1:
         print   1
         continue

      if ov == DUMMY:
         ov = 2*math.log(math.log(n))
      for factor in factors(n, veb, ra, ov, pr):
         print factor

def interactive(veb, ra, ov, pr):
   print   'pyecm v. %s (interactive mode):' % VERSION
   print   'Type "exit" at any time to quit.'
   print

   response = raw_input()
   while response != 'exit' and response != 'quit':
      if valid_input(response):
         response = response.replace('^', '**')
         try:
            n = eval(response)
            int(n)
         except (SyntaxError, TypeError, ValueError):
            help()
      else:
         help()

      print   'Factoring number %d:' % n
      if n < 0:
         print   -1
         n = -n
      if n == 0:
         print   '0 does not have a well-defined factorization.'
         print
         response = raw_input()
         continue
      elif n == 1:
         print   1
         print
         response = raw_input()
         continue

      if ov == DUMMY:
         ov = 2*math.log(math.log(n))
      for factor in factors(n, veb, ra, ov, pr):
         print   factor
      print
      response = raw_input()

def main():
   ra = veb = False
   pr = 1.0
   ov = DUMMY
   for item in sys.argv[1:]:
      if item == '--help':
         help()
      elif item == '--noverbose':
         veb = False
      elif item == '--random':
         ra = veb = True
      elif item == '--verbose':
         veb = True
      elif item[:10] == '--portion=':
         pr = parse_switch(item, 'portion')
         ra = veb = True
      elif item[:5] == '--ov=':
         ov = parse_switch(item, 'ov')
      elif len(item) >= 2 and item[0] == '-' and item[1] != '-': # Short switch
         for char in item:
            if char == 'h':
               help()
            elif char == 'n':
               veb = False
            elif char == 'r':
               ra = veb = True
            elif char == 'v':
               veb = True
      else:
         if not valid_input(item):
            print 'I am confused about the following: "%s". Here\'s the help page:' % item
            print
            help()

   if len(sys.argv) > 1 and not is_switch(sys.argv[-1]):
      command_line(veb, ra, ov, pr)
   else:
      interactive(veb, ra, ov, pr)

if __name__ == '__main__':
   try:
      main()
   except (EOFError, KeyboardInterrupt):
      sys.exit()
