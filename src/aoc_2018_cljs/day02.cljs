(ns aoc-2018-cljs.day02
  (:require [aoc-2018-cljs.util :refer [slurp-resource line-seq-resource]]))

(def codes (->> "day02.txt"
                       line-seq-resource))

(defn map-frequencies
  [codes]
  (map frequencies codes))

(defn count-matches
  [target freqs]
  (let [vals-set (set (vals freqs))]
    (if (contains? vals-set target)
        1
        0)))

(defn checksum
  [codes]
  (let [freqs (map-frequencies codes)
        twos (reduce + (map #(count-matches 2 %) freqs))
        threes (reduce + (map #(count-matches 3 %) freqs))]
    (* twos threes)))

(defn hamming [s1 s2]
  (let [h-dist (count (filter true? (map (partial reduce not=) (map vector s1 s2))))]
    h-dist))

(defn cart [colls]
  (if (empty? colls)
    '(())
    (for [x (first colls)
          more (cart (rest colls))]
      (cons x more))))

(defn hamming-bin [codes]
  (let [h-dists (group-by #(apply hamming %) (cart [codes codes]))]
    (get h-dists 1)))

; Part 1
(checksum codes)

; Part 2
(hamming-bin codes)
