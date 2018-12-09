(ns aoc-2018-cljs.day06
  (:require [aoc-2018-cljs.util :refer [to-int cart slurp-resource line-seq-resource]]
            [clojure.string :as string]))

(def polymer (->> "day06.txt"
                  line-seq-resource))
