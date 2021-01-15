using System.Text;

namespace ReportGenerator
{
    public class SalaryFirstFormatter: IFormatter
    {
         public string Format(Employee employee)
        {
            var builder = new StringBuilder();
            builder.AppendLine("------------------");
            builder.AppendLine(string.Format("Salary: {0}", employee.Salary));
            builder.AppendLine(string.Format("Name: {0}", employee.Name));
            builder.AppendLine(string.Format("Age: {0}", employee.Age));
            builder.AppendLine("------------------");

            return builder.ToString();
        }
    }
}