package com.nighthawk.spring_portfolio;

public abstract class Fibonacci {
        protected int n;
    
        public Fibonacci(int n) {
            this.n = n;
        }
    
        public int calculate() {
            if (n <= 0) {
                return 0;
            } else if (n == 1) {
                return 1;
            } else {
                int a = 0, b = 1;
                for (int i = 2; i <= n; i++) {
                    int temp = a;
                    a = b;
                    b = temp + b;
                }
                return a;
            }
        }
    }