package org.onedigit.study.java.enumeration;

public class EnumTest {
	enum Colour {
		RED, GREEN, BLUE
	}
	
	public static void f(Colour colour)
	{
		switch (colour) {
		case GREEN:
			System.out.println("Green");
			break;
		default:
			System.out.println("Not green");
		}
	}
	
	public static void main(String... args)
	{
		Colour colour = Colour.RED;
		f(colour);
	}
}
