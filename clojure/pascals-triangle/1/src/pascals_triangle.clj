(ns pascals-triangle)

(defn- row [prev-row]
  (vec (concat [1] (map #(apply + %) (partition 2 1 prev-row)) [1])))

(def triangle (iterate row [1]))
