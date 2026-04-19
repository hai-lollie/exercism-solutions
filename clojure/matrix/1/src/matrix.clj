(ns matrix
  (:require [clojure.string :as str]))

(defn- parse-matrix [s]
  (->> (str/split-lines s)
       (mapv #(mapv parse-long (re-seq #"\d+" %)))))

(defn get-row
  "Returns the i-th row of the matrix."
  [matrix-str i]
  (nth (parse-matrix matrix-str) (dec i)))

(defn get-column
  "Returns the i-th column of the matrix."
  [matrix-str i]
  (let [rows (parse-matrix matrix-str)]
    (nth (apply mapv vector rows) (dec i))))
