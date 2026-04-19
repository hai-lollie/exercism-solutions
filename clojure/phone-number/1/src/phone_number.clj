(ns phone-number
  (:require [clojure.string :as str]))

(def ^:private invalid "0000000000")

(defn- ->ten [digits]
  (case (count digits)
    10  digits
    11  (when (str/starts-with? digits "1") (subs digits 1))
    nil))

(defn- valid? [ten]
  (and (not (#{\0 \1} (nth ten 0)))
       (not (#{\0 \1} (nth ten 3)))))

(defn number [input]
  (let [digits (when-not (re-find #"[^\d ()\-\.+]" input)
                 (str/replace input #"[^\d]" ""))
        ten    (some-> digits ->ten)]
    (if (some-> ten valid?)
      ten
      invalid)))
