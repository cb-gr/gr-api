(ns gr-parser.core
  (:require [clojure.string :as str]))

(def delimitters
  "A map of the record's type of delimitter to a regex to split the
  record"
  {:pipe  #" \| "
   :comma #", "
   :space #" "})

(def fields [:first-name :last-name :gender :favorite-color :date-of-birth])
(defrecord Person [first-name last-name gender favorite-color date-of-birth])

(defn parse-line
  [delimitter-regex line]
  (->> (str/split line delimitter-regex)
       (map hash-map fields)
       (reduce merge)
       (map->Person)))


