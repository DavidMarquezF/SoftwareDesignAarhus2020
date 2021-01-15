namespace CompressionStocking
{
    public interface IPump
    {
         void Run();
         void RunBackwards();
         void Stop();
    }
}