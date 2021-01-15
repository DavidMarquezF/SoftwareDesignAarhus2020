using System;

namespace Hello_World {
    public class DoHickey : IDoThings {
        public void DoNothing () {
            Console.WriteLine ("DoHickey::DoNothing()");
        }

        public int DoSomething (int number) {
            Console.WriteLine ("DoHickey::DoNothing():" + number.ToString ());
            return number;
        }

        public string DoSomethingElse (string input) {
            Console.WriteLine ("DoHickey::DoSomethingElse(): " + input);
            return input;
        }
    }
}