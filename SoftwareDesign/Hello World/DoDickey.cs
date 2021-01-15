using System;

namespace Hello_World {
    public class DoDickey : IDoThings {
        public void DoNothing () {
            Console.WriteLine ("DoDickey::DoNothing()");
        }

        public int DoSomething (int number) {
            Console.WriteLine ("DoDickey::DoNothing():" + number.ToString ());
            return number;
        }

        public string DoSomethingElse (string input) {
            Console.WriteLine ("DoDickey::DoSomethingElse(): " + input);
            return input;
        }
    }
}