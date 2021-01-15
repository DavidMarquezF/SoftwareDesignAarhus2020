using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace ParallelMapReduce
{

    public static class MapReducer
    {

        public static ParallelQuery<TResult> MapReduce<TSource, TMapped, TKey, TResult>(
                    this ParallelQuery<TSource> source,
                    Func<TSource, IEnumerable<TMapped>> map,
                    Func<TMapped, TKey> keySelector,
                    Func<IGrouping<TKey, TMapped>, IEnumerable<TResult>> reduce)
        {

            return source
                .SelectMany(map)
                .GroupBy(keySelector)
                .SelectMany(reduce);
        }

        public static void DoIT()
        {
            var files =
                            Directory.EnumerateFiles("/home/david/Downloads/cards", "*.txt")
                                .AsParallel();

            var wordCount = files.MapReduce(
                path => File.ReadLines(path).Select(el => el.Split(",")),
                map => map[0],
                group => new []{
                    new KeyValuePair<string,int>(group.Key, group.Sum(el => {
                    return Int32.Parse(el[1]);
                    }))
                });
        }
    }
}