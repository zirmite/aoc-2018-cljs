(ns aoc-2018-cljs.day04
  (:require [aoc-2018-cljs.util :refer [to-int cart slurp-resource line-seq-resource]]
            [clojure.string :as string]))

(def codes (->> "day04.txt"
                line-seq-resource))

(defn datetime-matcher []
  (re-pattern "\\[(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d) (\\d\\d):(\\d\\d)\\]"))

(defn guard-matcher []
  (re-pattern "Guard #(\\d+)\\s"))

(defn action-matcher []
  (re-pattern "falls asleep|wakes up"))

(def guard-atom (atom 0))
(def action-atom (atom :awake))

(defn parse-line [code]
  (let [[_ year month day hour minute] (re-find (datetime-matcher) code)
        guard-str (get (re-find (guard-matcher) code) 1)
        guard (reset! guard-atom (or guard-str @guard-atom))
        action-string (re-find (action-matcher) code)
        action (case action-string
                     "falls asleep" (reset! action-atom :asleep)
                     "wakes up" (reset! action-atom :awake)
                     @action-atom)]
    (assoc {} :code code :year (js/parseInt year) :month (js/parseInt month) :day (js/parseInt day)
               :hour (js/parseInt hour) :minute (js/parseInt minute)
               :guard (js/parseInt guard) :action action)))

(comment
  (def code1 (first codes))
  (parse-date code1)

  (def maps
    (map #(assoc {}
                 :code %
                 :date (parse-date %)
                 :hour (parse-hour %)
                 :minute-ten (first (parse-minute %))
                 :minute-one (second (parse-minute %))) codes))

  (def sorted (sort-by :date maps))
)
