using System;

namespace Hello_World {
    class Program {
        static void Main (string[] args) {
            string key;
            do {
                Console.Write ("Please enter if you want to use DoHickey (1) or DoDickey(2)");
                key = Console.ReadLine();

            }while(key != "1" && key != "2");
            
            IDoThings myStuff = key == "1" ? (IDoThings)new DoHickey() : (IDoThings)new DoDickey();
            myStuff.DoNothing ();
            myStuff.DoSomething (1);
            myStuff.DoSomethingElse ("Hello");
        }
    }
}