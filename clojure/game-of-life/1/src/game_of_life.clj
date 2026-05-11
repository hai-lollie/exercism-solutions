(ns game-of-life)

(def ^:private alive 1)
(def ^:private dead  0)

(defn- neighbors [grid rows cols row col]
  (for [dr [-1 0 1]
        dc [-1 0 1]
        :when (not (and (zero? dr) (zero? dc)))
        :let [r (+ row dr)
              c (+ col dc)]
        :when (and (< -1 r rows) (< -1 c cols))]
    (get-in grid [r c])))

(defn- live-neighbor-count [grid rows cols row col]
  (count (filter #(= alive %) (neighbors grid rows cols row col))))

(defn- next-cell-state [cell live-neighbors]
  (cond
    (and (= alive cell) (#{2 3} live-neighbors)) alive
    (and (= dead  cell) (= 3 live-neighbors))    alive
    :else                                        dead))

(defn tick [grid]
  (let [rows (count grid)
        cols (count (first grid))]
    (vec (for [row (range rows)]
           (vec (for [col (range cols)]
                  (next-cell-state (get-in grid [row col])
                                   (live-neighbor-count grid rows cols row col))))))))
