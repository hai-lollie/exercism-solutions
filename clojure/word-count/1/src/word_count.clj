(ns word-count
  (:require [clojure.string :as str]))

(defn word-count
  "Counts how many times each word occurs in the given string."
  [s]
  (->> (str/lower-case s)
       (re-seq #"[a-z0-9']+")
       (map #(str/replace % #"^'+|'+$" ""))
       (remove str/blank?)
       frequencies))
