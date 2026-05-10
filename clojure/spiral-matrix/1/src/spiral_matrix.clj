(ns spiral-matrix)

(defn spiral [n]
  (letfn [(build [size]
            (cond
              (zero? size) []
              (= size 1)   [[(* n n)]]
              :else
              (let [start (inc (- (* n n) (* size size)))
                    [te re be le] (rest (reductions + start [size (dec size) (dec size) (- size 2)]))]
                (into [(vec (range start te))]
                      (conj (mapv (fn [l row r] (-> [l] (into row) (conj r)))
                                  (range (dec le) (dec be) -1)
                                  (build (- size 2))
                                  (range te re))
                            (conj (vec (range (dec be) (dec re) -1)) (dec re)))))))]
    (build n)))
