#!/usr/bin/python
import sys

def pos (a, b, k):
	return ((a-1)*k + b - a * (a+1) / 2)
def exp (a, b, k):
	return k*(k-1)/2 - ((a-1)*k + b - a * (a+1) / 2)

def printAll(k, function):
	for a in range(1,k+1):
		for b in range(1,k+1):
			if (a==b):
				sys.stdout.write(' - ')
			elif (a>=b):
				sys.stdout.write('   ')
			else:
				i = function(a,b,k)
				if (i < 10):
					sys.stdout.write('  %i' % i)
				else:
					sys.stdout.write(' %i' % i)
		print ''


printAll(10, pos)