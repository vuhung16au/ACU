https://liveexample.pearsoncmg.com/html/ShowInnerClass.html
https://liveexample.pearsoncmg.com/html/AnonymousHandlerDemo.html

19
Inner Classes
Inner class: A class is a member of another 
class.
Advantages: In some applications, you can 
use an inner class to make programs simple.
An inner class can reference the data and 
methods defined in the outer class in which it 
nests, so you do not need to pass the 
reference of the outer class to the constructor 
of the inner class.

Inner Classes (cont.)
Inner classes can make programs simple and 
concise. 
An inner class supports the work of its containing 
outer class and is compiled into a class named 
OuterClassName$InnerClassName.class. For 
example, the inner class InnerClass in OuterClass 
is compiled into OuterClass$InnerClass.class.

Inner Classes (cont.)
An inner class can be declared public, protected, 
or private subject to the same visibility rules 
applied to a member of the class. 
An inner class can be declared static. A static 
inner class can be accessed using the outer 
class name. A static inner class cannot access 
nonstatic members of the outer class

Anonymous Inner Classes
An anonymous inner class must always extend a 
superclass or implement an interface, but it cannot have 
an explicit extends or implements clause. 
An anonymous inner class must implement all the abstract 
methods in the superclass or in the interface. 
An anonymous inner class always uses the no-arg 
constructor from its superclass to create an instance. If an 
anonymous inner class implements an interface, the 
constructor is Object().
An anonymous inner class is compiled into a class named 
OuterClassName$n.class. For example, if the outer class 
Test has two anonymous inner classes, these two classes 
are compiled into Test$1.class and Test$2.class.

Anonymous Inner Classes (cont.)
Inner class listeners can be shortened using 
anonymous inner classes. An anonymous inner 
class is an inner class without a name. It 
combines declaring an inner class and creating an 
instance of the class in one step. An anonymous 
inner class is declared as follows:
new SuperClassName/InterfaceName() {
  // Implement or override methods in superclass or interface
  // Other methods if necessary
}
