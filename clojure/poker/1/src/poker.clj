(ns poker
  (:require [clojure.string :as str]))

(defn- rank-value [r]
  (case r
    "A" 14 "K" 13 "Q" 12 "J" 11 "10" 10
    (Integer/parseInt r)))

(defn- ->card [card]
  (let [suit (str (last card))
        rank (apply str (butlast card))]
    {:rank (rank-value rank) :suit suit}))

(defn- rank-freq [ranks]
  (sort-by (fn [[r c]] [(- c) (- r)]) (frequencies ranks)))

(defn- tie-break-ranks [groups]
  (mapcat (fn [[r c]] (repeat c r)) groups))

(defn- flush? [suits]
  (= 1 (count (distinct suits))))

(defn- wheel? [ranks]
  (= (sort ranks) [2 3 4 5 14]))

(defn- straight? [ranks]
  (let [sorted-asc (sort ranks)]
    (or (wheel? ranks)
        (and (= 5 (count (distinct ranks)))
             (= 4 (- (last sorted-asc) (first sorted-asc)))))))

(defn- straight-ranks [ranks]
  (if (wheel? ranks) [5 4 3 2 1] (sort > ranks)))

(defn- hand-rank [groups flush? straight?]
  (let [[[_ top-count] [_ next-count]] groups]
    (cond
      (and flush? straight?)              8
      (= top-count 4)                     7
      (and (= top-count 3)
           (= next-count 2))              6
      flush?                              5
      straight?                           4
      (= top-count 3)                     3
      (and (= top-count 2)
           (= next-count 2))              2
      (= top-count 2)                     1
      :else                               0)))

(defn- score-hand [hand]
  (let [cards  (map ->card (str/split hand #" "))
        ranks  (map :rank cards)
        suits  (map :suit cards)
        groups (rank-freq ranks)
        hrank  (hand-rank groups (flush? suits) (straight? ranks))]
    (into [hrank]
          (if (#{4 8} hrank) (straight-ranks ranks) (tie-break-ranks groups)))))

(defn best-hands [hands]
  (->> hands
       (group-by score-hand)
       (sort-by key)
       last
       val))
