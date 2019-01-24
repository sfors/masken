(ns masken.main
  (:require [rum.core :as rum]
            [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [masken.view.app-component :refer [app-component]]))

(defonce state-atom (atom {:title "Masken!"}))

(defn main!
  []
  (println "Hej!"))

(defn button-clicked-handler
  [{db :db} [event-name data]]
  {:db (if (get db :counter)
         (update db :counter inc)
         (assoc db :counter 1))})


(defn query-counter
  [db v]
  (:counter db))


(re-frame/reg-event-fx
  :button-clicked
  button-clicked-handler)


(re-frame/reg-sub
  :query-counter
  query-counter)


(defn render
  []
  (reagent/render [app-component] (js/document.getElementById "app")))

(defn on-reload
  {:dev/after-load true}
  []
  (render))