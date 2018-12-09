(ns aoc-2018-cljs.day05
  (:require [aoc-2018-cljs.util :refer [to-int cart slurp-resource line-seq-resource]]
            [clojure.string :as string]))

(def codes (->> "day05.txt"
                line-seq-resource))

(defn react [_ a b]
  (let [a-up-b-low (if
                     (and
                      (not= (string/lower-case a) a)
                      (not= (string/upper-case b) b)
                      (= (string/lower-case a) (string/lower-case b))) true false)
        a-low-b-up (if
                     (and
                      (not= (string/lower-case b) b)
                      (not= (string/upper-case a) a)
                      (= (string/lower-case a) (string/lower-case b))) true false)]
    (if (or a-up-b-low a-low-b-up)
      ""
      (str a b))))

(comment
  (def re-pair (re-pattern "(.)(.)"))
  (def str1 "XxSsdDIijNnJACszZScZfFhHQyYrRqzdXTtxDEeNnGgOaAcCMcCL")
  (string/replace str1 re-pair "b")
)
