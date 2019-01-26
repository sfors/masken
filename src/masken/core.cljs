(ns masken.core)

(defn create-db []
      {:time 0
       "1"   {:counter 0}
       "2"   {:counter 0}
       :worm [[5 5] [6 5] [7 5] [7 6]]})

(defn move-in-direction [db direction-key]
      (update db :worm (fn [worm]
                           (let [direction-vector (case direction-key
                                                        :left [-1 0]
                                                        :up [0 -1]
                                                        :right [1 0]
                                                        :down [0 1])]
                                (cons (map + (first worm) direction-vector)
                                      (drop-last worm))))))

