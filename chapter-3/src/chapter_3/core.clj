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

;; Symmetrizer
(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts
         final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining
               (into final-body-parts
                     (set [part (matching-part part)])))))))


;; Multi-arity recurive function example
(defn recursive-printer
  ([]
   (recursive-printer 0))
  ([iteration]
   (println iteration)
   (if (> iteration 3)
     (println "Goodbye!")
     (recursive-printer (inc iteration)))))

;; My reducer
(defn my-reduce
  ([f initial coll]
   (loop [result initial
          remaining coll]
     (if (empty? remaining)
       result
       (recur (f result (first remaining)) (rest remaining)))))
  ([f [head & tail]]
   (my-reduce f head tail)))

;; Reimplementation of the symmetrizer with reduce:
(defn better-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set [part (matching-part part)])))
          []
          asym-body-parts))

(defn hit
  [asym-body-parts]
  (let [sym-parts (better-symmetrize-body-parts asym-body-parts)
        body-part-size-sum (reduce + (map :size sym-parts))
        target (rand body-part-size-sum)]
    (loop [[part & remaining] sym-parts
           accumulated-size (:size part)]
      (if (> accumulated-size target)
        [(:name part) (:size part) accumulated-size target] 
        (recur remaining (+ accumulated-size (:size (first remaining))))))))

;; Ex. 5
;; Create a function that's similar to symmetrize-body-parts except that it has to work with weird space aliens with radial symmetry.
;; Instead of two eyes, arms, legs, and so on, they have five.
(defn matching-part-radial
  [part]
  (if (.contains (:name part) "left-")
    [{:name (clojure.string/replace (:name part) #"^left-" "1-")
      :size (:size part)}
     {:name (clojure.string/replace (:name part) #"^left-" "2-")
      :size (:size part)}
     {:name (clojure.string/replace (:name part) #"^left-" "3-")
      :size (:size part)}
     {:name (clojure.string/replace (:name part) #"^left-" "4-")
      :size (:size part)}
     {:name (clojure.string/replace (:name part) #"^left-" "5-")
      :size (:size part)}]
    [part]))

(defn symmetrize-body-parts-radial
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts num]
  (loop [remaining-asym-parts asym-body-parts
         final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining
               (into final-body-parts
                     (matching-part-radial part num)))))))

;; Exercise 6
;; Create a function that generalizes symmetrize-body-parts and the function you created in exercise 5.
;; The new function should take a collection of body parts and the number of matching body parts to add.
(defn matching-part-radial
  [part num]
  (if (.contains (:name part) "left-")
    (loop [cnt num
           buff []]
      (if (zero? cnt)
        buff
        (recur (dec cnt)
               (concat  buff  [{:name (clojure.string/replace (:name part) #"^left-" (str cnt "-"))
                                :size (:size part)}]))))
    [part]))

