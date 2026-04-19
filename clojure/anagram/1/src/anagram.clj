(ns anagram
  (:require [clojure.string :as str]))

(defn- canonical [word]
  (sort (str/lower-case word)))

(defn anagrams-for [word candidates]
  (let [word-lower (str/lower-case word)
        word-canon (canonical word)]
    (filter (fn [candidate]
              (and (not= (str/lower-case candidate) word-lower)
                   (= (canonical candidate) word-canon)))
            candidates)))
