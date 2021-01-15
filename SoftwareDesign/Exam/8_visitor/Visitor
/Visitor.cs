namespace Visitor_
{

    public interface IAarhusPerson
    {
        void Accept(IDiscountVisitor visitor);

    }
    public class Teacher : IAarhusPerson
    {
        public void Accept(IDiscountVisitor visitor)
        {
            visitor.visit(this);
        }
    }

    public class Student : IAarhusPerson
    {
        public void Accept(IDiscountVisitor visitor)
        {
            visitor.visit(this);
        }
    }
    public interface IDiscountVisitor
    {
        double visit(Teacher teacher);
        double visit(Student student);
    }


    public class BeerDiscount : IDiscountVisitor
    {
        public double visit(Teacher teacher)
        {
            return 0.8;
        }

        public double visit(Student student)
        {
            return 0.9;
        }
    }
    public class TripDiscount : IDiscountVisitor
    {
        public double visit(Teacher teacher)
        {
            return 0.1;
        }

        public double visit(Student student)
        {
            return 0.5;
        }
    }
    public class Visitor
    {

    }
}