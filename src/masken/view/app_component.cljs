(ns masken.view.app-component
  (:require [re-frame.core :as re-frame]))

(defn button-component
  []
  (let [counter-atom (re-frame/subscribe [:query-counter])
        counter (deref counter-atom)]
    (println "button-component rendered")
    [:button {:on-click (fn [] (re-frame/dispatch [:button-clicked "hej"]))} (str "Click me: " counter)]))


(defn app-component
  []
  [:div [:h2 "Masken!"]
   (println "appcomponent rendered")
   [button-component]])

