(ns matching-brackets)

(def ^:private opening-brackets #{\( \[ \{})

(def ^:private bracket-pairs {\) \(, \] \[, \} \{})

(defn- process-char [stack ch]
  (cond
    (opening-brackets ch) (conj stack ch)
    (bracket-pairs ch)    (if (= (peek stack) (bracket-pairs ch))
                            (pop stack)
                            (reduced ::invalid))
    :else                 stack))

(defn valid? [s]
  (= (reduce process-char [] s) []))
