(ns masken.view.app-component
  (:require [re-frame.core :as re-frame]))

(defn button-component
      [id]
      (let [counter-atom (re-frame/subscribe [:query-counter id])
            counter (deref counter-atom)]
           (println "button-component" id "rendered")
           [:button {:on-click (fn [] (re-frame/dispatch [:button-clicked id]))} (str "Click me: " counter)]))


(defn time-component
      []
      (let [time (deref (re-frame/subscribe [:query-time]))]
           [:div [:h2 (str "Time: " time)]]))

(defn board-component
      []
      (let [size 10
            [worm-head & worm-tail] (deref (re-frame/subscribe [:query-worm]))]
           [:div {:style {:position "relative"}}
            [:div {:key   "head"
                   :style {:position         "absolute"
                           :transition "all 200ms"
                           :transform        (str "translateX(" (* (first worm-head) 100) "%) "
                                                  "translateY(" (* (second worm-head) 100) "%)")
                           :background-color "tomato"
                           :border-radius    "50%"
                           :height           (str (/ 100 size) "%")
                           :width            (str (/ 100 size) "%")}}]
            (map-indexed (fn [index [x y]]
                             [:div {:key   (str "tail-" index)
                                    :style {:position         "absolute"
                                            :transition "all 200ms"
                                            :transform        (str "translateX(" (* x 100) "%) "
                                                                   "translateY(" (* y 100) "%)")
                                            :background-color "orange"
                                            :border-radius    "40%"
                                            :height           (str (/ 100 size) "%")
                                            :width            (str (/ 100 size) "%")}}])
                         worm-tail)
            (map (fn [y]
                     [:div {:key   y
                            :style {:display "flex"}}
                      (map (fn [x]
                               [:div {:key   x
                                      :style {:display          "inline-block"
                                              :width            (str (/ 100 size) "%")
                                              :border           "1px solid white"
                                              :background-color "lightgray"
                                              :padding-bottom   (str (/ 100 size) "%")}}])
                           (range size))])
                 (range size))]))

(defn app-component
      []
      [:div [:h2 "Masken!"]
       (println "appcomponent rendered")
       [button-component "1"]
       [button-component "2"]
       [time-component]
       [board-component]])

