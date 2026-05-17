(ns all-your-base)

(defn- valid-base? [base]
  (>= base 2))

(defn- valid-digits? [digits from-base]
  (every? (fn [d] (and (>= d 0) (< d from-base))) digits))

(defn- digits->value [digits from-base]
  (reduce (fn [acc d] (+ (* acc from-base) d)) 0 digits))

(defn- value->digits [value to-base]
  (if (zero? value)
    '(0)
    (loop [v value result '()]
      (if (zero? v)
        result
        (recur (quot v to-base) (cons (rem v to-base) result))))))

(defn convert [from-base digits to-base]
  (when (and (valid-base? from-base)
             (valid-base? to-base)
             (valid-digits? digits from-base))
    (if (empty? digits)
      '()
      (value->digits (digits->value digits from-base) to-base))))
