(ns pascals-triangle)

(defn- next-row [prev-row]
  (vec (concat [1]
               (map (fn [[a b]] (+ a b)) (partition 2 1 prev-row))
               [1])))

(def triangle (iterate next-row [1]))
