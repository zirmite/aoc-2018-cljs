(ns aoc-2018-cljs.day03
  (:require [aoc-2018-cljs.util :refer [cart slurp-resource line-seq-resource]]
            [clojure.string :as string]))

(def codes (->> "day03.txt"
                       line-seq-resource))

(defn parse-id [elements]
  (-> elements
      (get 0)
      (string/split #"#")
      (get 1)))

(defn to-int [xy]
  (map #(js/parseInt %) xy))

(defn parse-xy [elements]
  (-> elements
      (get 2)
      (string/split #":|,")
      (to-int)))

(defn parse-wh [elements]
  (-> elements
      (get 3)
      (string/split #"x")
      (to-int)))

(defn parse-code [code]
  (let [elements (string/split code #" ")
        [x, y] (parse-xy elements)
        [width, height] (parse-wh elements)
        xs (range (+ 0 x) (+ 0 x width))
        ys (range (- 0 y height) (- 0 y))
        xys (cart [xs ys])
        id (parse-id elements)]
    {:id id :xys xys}))

(defn check-xys [freqs id-xys]
  (assoc id-xys
         :check (reduce + (map #(get freqs %) (:xys id-xys)))
         :count (count (:xys id-xys))))

(defn increment-all [codes]
   (->> codes
        (map parse-code)
        (map :xys)
        (flatten)
        ((partial partition 2))
        (frequencies)))

(defn find-no-overlap [freqs codes]
  (let [ids-xys (map parse-code codes)
        checked (map #(check-xys freqs %) ids-xys)]
    (:id (filter #(= (:check %) (:count %)) checked))))





(comment
  (def code1 "#5 @ 919,43: 10x16")
  (def x 35)
  (def y 93)
  (def width 11)
  (def height 13)
  (def xs (range (+ 0 x) (+ 0 x width)))
  (def ys (range (- 0 y height) (- 0 y))))
