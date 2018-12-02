(ns aoc-2018-cljs.day01
  (:require [aoc-2018-cljs.util :refer [slurp-resource line-seq-resource]]))

(def freq-changes (->> "day01.txt"
                       line-seq-resource
                       (map #(js/parseInt %))))

(defn find-first-repeat
  [freq-changes]
  (let [redux (reductions + 0 (cycle freq-changes))]
    (reduce (fn [seen freq]
                (if (seen freq)
                  (reduced freq)
                  (conj seen freq)))
      #{}
      redux)))

; part 1
(reduce + freq-changes)

; part 2
(find-first-repeat freq-changes)
