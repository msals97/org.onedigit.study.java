package org.onedigit.study.java.classloader;


public class ClassLoaderTest
{
    class Util3
    {
        
    }
    
    class Factory3
    {
        public /* static */ Util3 instance()
        {
            return new Util3();
        }
        
        public /* static */ Object instanceUntyped()
        {
            return new Util3();
        }
        
        Object instancePackage()
        {
            return new Util3();
        }
    }
    
    
    class B
    {
        public void doSomethingElse()
        {
            
        }
    }
    
    class A
    {
        public void doSomething()
        {
            B b = new B(); // this is loaded by A's class loader
            b.doSomethingElse();
        }
    }
    
    class OurOwnClass
    {
        
    }
    
    public ClassLoaderTest()
    {
        System.out.println("System class loader = " + ClassLoader.getSystemClassLoader());
        System.out.println("Thread context class loader = " + Thread.currentThread().getContextClassLoader());
        
        System.out.println("Class loader hierarchy for this class:");
        String padding = "";
        ClassLoader cl = ClassLoaderTest.class.getClassLoader();
        while (cl != null) {
            System.out.println(padding + cl);
            cl = cl.getParent();
            padding += "       ";
        }        
    }
    public static void main(String... args)
    {
        ClassLoaderTest test = new ClassLoaderTest();
        
        OurOwnClass clz = test.new OurOwnClass();
        // The chain of class loader calls will be like this:
        // AppClassLoader -> ExtClassLoader -> Bootstrap
        //   Bootstrap cannot find the class, so will delegate back to ExtClassLoader.
        //   ExtClassLoader cannot find it either, so finally ends up back at AppClassLoader
        // Bootstrap -> ExtClassLoader -> AppClassLoader
        System.out.println("Our own class is loaded by: " + clz.getClass().getClassLoader());
        
        // Visibility:
        // Classes loaded by parent class loaders are visible to child class laoders, but not
        // vice versa, i.e., children can see their parent's classes, but the parent class 
        // loaders cannot see classes loaded by their child class loaders.
        // Sibling class loaders cannot see each other's classes either.
        
        // Uniqueness:
        // When a class loader loads a class, the child class loaders in the hierarchy will
        // never reload the class.  This follows from the delegation principle, since a classloader
        // always delegates to its parents.
        // The child classloader will load (or try to load) it only when the parents fail.
        
        // ClassLoader API
        // URL getResource(String name); - very useful for debugging.
        // name is the classpath?
        // 
        // -verbose:class
        //
        // In a servlet
        // public class Test1 extends HttpServlet {
        // protectec void doGet(....) {
        //  PrintWriter out = response.getWriter();
        //  Print the runtime classpath of the Test1 classe's classloader:
        //  out.print(Arrays.toString(((URLClassLoader)Test1.class.getClassLoader()).getURLs()));
        // 
        // Get the full directory, package name of the class that's loaded
        // out.print(Test2.class.getClassLoader().getResource(Util2.class.getName().replace('.','/') + ".class"));
        //
        // out.println(((Util3) Factory3.instanceUntyped()).sayHello());
        // Exception: Util3 cannot be cast to Util3
        //
        // Shared Class Loader (Util3, Factory3), e.g. lib directory of Tomcat
        //             ^
        //             |
        // Web Class Loader (Util3, Test3)
        // Util3 is created by the Shared class loader
        // The web class loader tries to cast to its version of Util3
        // }
        //
        // ClassNotFoundException
        // ClassNoDefFoundException
        //
        // javap -private Util2
        //
        // out.println(Factory3.instance().sayHello());
        // Exception: java.lang.LinkageError: loader constraint violation: 
        // when resolving method "Factory3.instance()LUtil3;" ...
        // Error due to similar class loader issue as before.
        // 
        //
        // out.println(Factory3.instancePackage().sayHello());
        // IllegalAccessError: tried to access method Factory3.instancePackage()Ljava/lang/Object; ...
        // the class is loaded from the shared class loader, so the package is not visible to
        // the web class loader
        //
        // Find out where Util3 is coming from:
        // out.print(Test3.class.getClassLoader().getResource(Util3.class.getName().replace('.','/') + ".class"));
        // out.print(Factory3.class.getClassLoader().getResource(Util3.class.getName().replace('.','/') + ".class"));
    }
    
}
