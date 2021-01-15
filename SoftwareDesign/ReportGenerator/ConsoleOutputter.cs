using System;

namespace ReportGenerator
{
    public class ConsoleOutputter : IOutput
    {
        public void WriteLine(string text)
        {
            Console.WriteLine(text);
        }
    }
}