(ns fwpd.core
  (:require [clojure.string :as str])
  (:gen-class))

(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int [s]
  (Integer. s))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  [s]
  (map (fn [e] (str/split e #","))
       (str/split s #"\n")))

(defn mapify [rows]
  (map
   (fn [unmapped]
     (reduce
      (fn [row-map [vamp-key value]]
        (assoc row-map vamp-key (convert vamp-key value)))
      {}
      (map vector vamp-keys unmapped)))
   rows))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (->> filename
       slurp
       parse
       mapify
       prn))
