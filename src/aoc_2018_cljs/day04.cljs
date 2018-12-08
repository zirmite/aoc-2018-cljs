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
  (re-pattern "falls asleep|wakes up|begins shift"))

(def guard-atom (atom 0))
(def action-atom (atom :awake))

(defn parse-line [code]
  (let [[_ year month day hour minute] (re-find (datetime-matcher) code)
        guard-str (get (re-find (guard-matcher) code) 1)
        guard (reset! guard-atom (or guard-str @guard-atom))
        action-string (re-find (action-matcher) code)
        action (case action-string
                     "begins shift" (reset! action-atom :begins)
                     "falls asleep" (reset! action-atom :asleep)
                     "wakes up" (reset! action-atom :awake)
                     @action-atom)]
    (assoc {} :code code :year (js/parseInt year) :month (js/parseInt month) :day (js/parseInt day)
               :hour (js/parseInt hour) :minute (js/parseInt minute)
               :guard (js/parseInt guard) :action action)))

(defn final [codes]
  (let [parsed (map parse-line codes)
        sorted (map #(parse-line (:code %)) (sort-by (juxt :year :month :day :hour :minute) parsed))
        filtered (filter #(or (= (:action %) :asleep) (= (:action %) :awake)) sorted)]
    (map #(parse-line (:code %)) filtered)))

(defn sum-minutes [events]
  (reduce + (map
               #(- (:minute (nth % 1)) (:minute (nth % 0))) (partition 2 events))))

(defn minutes-range [events]
  (conj '() (map #(range (:minute (nth % 0)) (- (:minute (nth % 1)) 0) 1) (partition 2 events))))

(defn total-by-guard [codes]
  (let [all-good (final codes)
        grouped (group-by (juxt :year :month :day :guard) all-good)
        total-by-day-guard (map #(assoc {} :group % :guard (:guard (nth (get grouped %) 0)) :minute-sum (sum-minutes (get grouped %)) :minutes (minutes-range (get grouped %))) (keys grouped))]
    (group-by :guard total-by-day-guard)))

(defn max-id [totals]
  (map #(assoc {} :id % :total (reduce + (map :minute-sum (get totals %)))) (keys totals)))

(defn max-minute [totals]
  (map #(assoc {} :id % :freqs (frequencies (flatten (reduce conj '() (map :minutes (get totals %)))))) (keys totals)))

(comment
  (def code1 (first codes))
  (def t-by-g (total-by-guard codes))
  (def mid (max-id t-by-g))
  (def max-min (max-minute t-by-g)))
