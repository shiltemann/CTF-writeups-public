#! /usr/bin/perl
use Crypt::OpenSSL::RSA;
use Crypt::OpenSSL::Bignum;
 
my $p = Crypt::OpenSSL::Bignum->new_from_hex('B25150A158FF4CB52D99B2749FD007BB');
my $q = Crypt::OpenSSL::Bignum->new_from_hex('CD0FC0AB1622451A651B2662BA17F2CB');
my $rsa = Crypt::OpenSSL::RSA->new_key_from_parameters($n, $e, undef, $p, $q);
@par = $rsa->get_key_parameters(); #gives an array of parameters including d, N, e, etc.
 
my $ctx = Crypt::OpenSSL::Bignum::CTX->new();
 
$enc = Crypt::OpenSSL::Bignum->new_from_hex('39C9FB8503B3F73BB24069AFE0F2C0416177A40EE60E57134C00ABE8BEDE45BD'); #this is C
 
 
$dec = $enc->mod_exp($par[2], $n, $ctx); #this is C ^ d mod N
 
print $dec->to_bin()."\n";