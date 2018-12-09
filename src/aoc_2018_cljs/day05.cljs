(ns aoc-2018-cljs.day05
  (:require [aoc-2018-cljs.util :refer [to-int cart slurp-resource line-seq-resource]]
            [clojure.string :as string]))

(def polymer (first (->> "day05.txt"
                       line-seq-resource)))

(defn react? [a b]
 (and
    (= (string/lower-case b) (string/lower-case a))
    (not= a b)))

(defn build-chain [polymer unit]
  (if (some->
       (peek polymer)
       (react? unit))
    (pop polymer)
    (conj polymer unit)))

(defn react [polymer]
  (reduce build-chain [] polymer))

(def alphabet "abcdefghijklmnopqrstuvwxyz")

(defn remove-and-build [polymer bad-unit]
  (let [bad-re (re-pattern (str "(?i)" bad-unit))
        new-polymer (string/replace polymer bad-re "")]
    {:unit (keyword bad-unit) :count (count (react new-polymer))}))

(def all-units (map #(remove-and-build polymer %) alphabet))

(defn part-1 [polymer]
  (count (react polymer)))

(defn part-2 [polymer]
  (let [all-units (map #(remove-and-build polymer %) alphabet)]
    (:count (first (sort-by :count all-units)))))

(comment
  (def re-pair (re-pattern "(.)(.)"))
  (def str1 "XxSsdDIijNnJACszZScZfFhHQyYrRqzdXTtxDEeNnGgOaAcCMcCL")
  (string/replace str1 re-pair "b"))
