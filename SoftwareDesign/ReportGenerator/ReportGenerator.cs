using System;
using System.Collections.Generic;

namespace ReportGenerator
{
    public enum ReportOutputFormatType
    {
        NameFirst,
        SalaryFirst
    }

    internal static class ReportGenerator
    {
        public static void CompileReport(IEmployeeList employeeList, IOutput outputter, IFormatter formatter = null)
        {
            var employees = employeeList.GetEmployees();
            foreach(var employee in employees)
                outputter.WriteLine(formatter == null ? employee.ToString() : formatter.Format(employee));
        }
    }
}