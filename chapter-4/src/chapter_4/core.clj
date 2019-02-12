(ns chapter-4.core)

;; Lazy Seqs example
(def vampire-database
  {0 {:make-blood-puns? false :has-pulse? true :name "McFishwich"}
   1 {:make-blood-puns? false :has-pulse? true :name "McMackson"}
   2 {:make-blood-puns? true :has-pulse? false :name "Damon Salvatore"}
   3 {:make-blood-puns? true :has-pulse? true :name "Mickey Mouse"}})

(defn vampire-related-details
  [social-security-number]
  (Thread/sleep 100)
  (get vampire-database social-security-number))

(map vampire-related-details '(0 1 2 3))


