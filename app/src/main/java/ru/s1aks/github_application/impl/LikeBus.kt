package ru.s1aks.github_application.impl

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import ru.s1aks.github_application.impl.LikeEvent

enum class LikeEvent {
    PLUS_EVENT,
    MINUS_EVENT
}

class LikeBus {
    private val bus = PublishSubject.create<LikeEvent>()

    fun post(event: LikeEvent) {
        bus.onNext(event)
    }

    fun get(): Observable<LikeEvent> = bus
}