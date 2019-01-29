(ns chapter-3.core
  (:gen-class))

;; Ex 2
;; Write a function that takes a number and adds 100 to it.
(defn add100
  [num]
  (+ num 100))

;; Ex 3
;; Write a function, dec-maker, that works exactly like the function inc-maker except with subtraction:
(defn inc-maker
  "Create a custom incrementor"
  [inc-by]
  #(+ % inc-by))

(def inc3 (inc-maker 3))

(defn dec-maker
  "Create a custom decrementor"
  [dec-by]
  #(- % dec-by))

(def dec9 (dec-maker 9))

;; Ex 4
;; Write a function, mapset, that works like map except the return value is a set:
(defn mapset
  [f vec]
  (set 
   (map f vec)))
