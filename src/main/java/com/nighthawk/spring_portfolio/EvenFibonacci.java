package com.nighthawk.spring_portfolio;

public class EvenFibonacci extends Fibonacci {
    public EvenFibonacci(int n) {
        super(n);
    }

    @Override
    public int calculate() {
        int fib = super.calculate();
        return fib % 2 == 0 ? fib : 0;
    }
}