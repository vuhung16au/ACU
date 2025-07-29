Lecture 4, titled "**Generics**", is motivated by the need to understand compile warnings and to fix them, as illustrated by an `ArrayList` example.

The **key objectives** of this lecture are to enable students to:
*   Understand the **benefits of generics**.
*   Utilize **generic classes and interfaces**.
*   **Declare generic classes and interfaces**.
*   Grasp why **generic types improve reliability and readability**.
*   Declare and use **generic methods and bounded generic types**.
*   Use **raw types for backward compatibility**.
*   Understand **wildcard types and their necessity**.
*   Convert legacy code using JDK 1.5 generics.
*   Comprehend that **generic type information is erased by the compiler**, meaning all instances of a generic class share the same runtime class file.
*   Identify certain **restrictions on generic types** caused by type erasure.
*   Design and implement generic matrix classes.

**Core Concepts and Benefits of Generics**:
*   **What is Generics?** Generics provide the capability to **parameterize types**. This means you can define a class or method with generic types that can be replaced with concrete types by the compiler. For example, a generic stack can be defined to store elements of a generic type, then instantiated to hold `String` objects or `Number` objects.
*   **Why Generics?** The main advantage of generics is that they allow **errors to be detected at compile time rather than at runtime**. If you try to use a generic class or method with an incompatible object, a compile error will occur, which **improves reliability**. Prior to JDK 1.5, certain type mismatches would lead to runtime errors, but with generics, these are caught earlier.
*   **No Casting Needed**: When using generic `ArrayList` in JDK 1.5 and later, explicit casting is no longer required when retrieving elements, as the compiler knows the type.

**Declaring and Using Generics**:
*   **Generic Classes and Interfaces**: Generics can be applied to class and interface declarations, as demonstrated with `GenericStack`.
*   **Generic Methods**: Methods can also be declared with generic types, allowing them to operate on various types while maintaining type safety.
*   **Bounded Generic Types**: You can restrict the types that can be used for a generic parameter using `extends`. For example, `<E extends GeometricObject>` ensures that `E` must be `GeometricObject` or a subclass thereof, enabling method calls specific to `GeometricObject`.

**Raw Types and Wildcards**:
*   **Raw Types**: These are generic types used without specifying a type argument (e.g., `ArrayList list = new ArrayList();`). This is roughly equivalent to `ArrayList<Object>` and is generally **unsafe** because it loses type safety, potentially leading to runtime errors. They are primarily kept for **backward compatibility** with older Java code.
*   **Wildcards**: Wildcards (`?`) are used in generic code to provide flexibility in type matching. There are three types:
    *   **Unbounded wildcard (`?`)**: Represents any type.
    *   **Bounded wildcard (`? extends T`)**: Represents `T` or any subtype of `T`.
    *   **Lower bound wildcard (`? super T`)**: Represents `T` or any supertype of `T`.

**Type Erasure and Restrictions**:
*   **Type Erasure**: Java generics are implemented using **type erasure**. This means the **compiler uses generic type information during compilation but removes it afterwards**. Consequently, **generic information is not available at runtime**. This approach allows generic code to be backward-compatible with legacy code that uses raw types. For example, `ArrayList<String> list = new ArrayList<>();` is compiled into `ArrayList list = new ArrayList();` with an explicit cast `String state = (String)(list.get(0));` for runtime use.
*   **Shared Runtime Class File**: An important implication of type erasure is that a generic class is shared by all its instances, regardless of the actual generic type. So, `GenericStack<String>` and `GenericStack<Integer>` instances both use the *same* `GenericStack` class file at runtime.
*   **Restrictions on Generics**: Due to type erasure, there are several restrictions:
    *   Cannot create an instance of a generic type (e.g., `new E()`).
    *   Generic array creation is not allowed (e.g., `new E`).
    *   A generic type parameter of a class is not allowed in a static context.
    *   Exception classes cannot be generic.

**Case Study**:
*   The lecture includes a case study on **Designing Generic Matrix Classes**, providing a generic class for matrix arithmetic (addition and multiplication) applicable to various matrix types, such as integer matrices and rational matrices.

This lecture provides a comprehensive understanding of Java generics, emphasizing their role in compile-time type checking, improving code reliability and readability, and the underlying mechanism of type erasure.

Important Facts 
It is important to note that a generic class 
is shared by all its instances regardless of 
its actual generic type. 
GenericStack<String> stack1 = new GenericStack<>();
GenericStack<Integer> stack2 = new GenericStack<>();
Although GenericStack<String> and 
GenericStack<Integer> are two types, but there is only one 
class GenericStack loaded into the JVM. 

Restrictions on Generics 
Restriction 1: Cannot Create an Instance of a Generic Type. (i.e., new E()).
Restriction 2: Generic Array Creation is Not Allowed. (i.e., new E[100]).
Restriction 3: A Generic Type Parameter of a Class Is Not Allowed in a Static Context.
Restriction 4: Exception Classes Cannot be Generic.

Sample codes: 

- https://liveexample.pearsoncmg.com/html/GenericStack.html
- https://liveexample.pearsoncmg.com/html/WildCardNeedDemo.html
- https://liveexample.pearsoncmg.com/html/AnyWildCardDemo.html
- https://liveexample.pearsoncmg.com/html/SuperWildCardDemo.html
- https://liveexample.pearsoncmg.com/html/TestArrayListNew.html

- https://liveexample.pearsoncmg.com/html/GenericMatrix.html
- https://liveexample.pearsoncmg.com/html/IntegerMatrix.html
- https://liveexample.pearsoncmg.com/html/TestIntegerMatrix.html
- https://liveexample.pearsoncmg.com/html/RationalMatrix.html
- https://liveexample.pearsoncmg.com/html/TestRationalMatrix.html