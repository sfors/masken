(ns masken.view.app-component
  (:require [re-frame.core :as re-frame]))

(defn button-component
  [id]
  (let [counter-atom (re-frame/subscribe [:query-counter id])
        counter (deref counter-atom)]
    (println "button-component" id "rendered")
    [:button {:on-click (fn [] (re-frame/dispatch [:button-clicked id]))} (str "Click me: " counter)]))


(defn app-component
  []
  [:div [:h2 "Masken!"]
   (println "appcomponent rendered")
   [button-component "1"]
   [button-component "2"]]) 

