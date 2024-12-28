import org.w3c.dom.css.Rect;

import java.util.Scanner;

// Define the abstract Shape class with an abstract area() method
abstract class Shape {
    abstract double getArea();
}

// Implement the Rectangle class that extends Shape
class Rectangle extends Shape {
    double length;
    double width;

    public Rectangle(double length, double width){
        this.length = length;
        this.width = width;
    }

    @Override
    double getArea() {
        double area = length*width;
        return area;
    }
}
// Implement the Circle class that extends Shape
class Circle extends Shape {
    double radius;

    public Circle(double radius){
        this.radius = radius;
    }

    @Override
    double getArea() {
        double area = Math.PI*Math.pow(radius,2);
        return area;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read the dimensions of the rectangle
        double length = scanner.nextDouble();
        double width = scanner.nextDouble();

        // Read the radius of the circle
        double radius = scanner.nextDouble();

        // Create instances of Rectangle and Circle
        Rectangle rectangle = new Rectangle(length, width);
        Circle circle = new Circle(radius);

        // Calculate and print the area of the rectangle
        System.out.println(rectangle.getArea());
        // Calculate and print the area of the circle
        System.out.println(circle.getArea());
        scanner.close();
    }
}