package com.nighthawk.spring_portfolio;

public class OddFibonacci extends Fibonacci {
    public OddFibonacci(int n) {
        super(n);
    }

    @Override
    public int calculate() {
        int fib = super.calculate();
        return fib % 2 != 0 ? fib : 0;
    }
}