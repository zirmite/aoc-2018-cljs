(ns aoc-2018-cljs.day08
  (:require [aoc-2018-cljs.util :refer [to-int cart slurp-resource line-seq-resource]]
            [clojure.string :as string]
            [cljs.reader :as edn]))

(def read-string edn/read-string)

(def codes (->> "day08.txt"
                line-seq-resource))

(defn parse-license [input]
  (letfn [(parse-node [[child-count metadata-count & more]]
            (let [[children more] (nth (iterate parse-child [[] more]) child-count)
                  [metadata more] (split-at metadata-count more)]
              [{:children children, :metadata metadata} more]))
          (parse-child [[children more]]
            (let [[child more] (parse-node more)]
              [(conj children child) more]))]
    (first (parse-node (map read-string (re-seq #"\d+" input))))))

(comment
  (count (string/split codes #" "))
  (def example "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2")
  (def by-hand {:1 {:children {} :metadata [1 1 2]}}))
