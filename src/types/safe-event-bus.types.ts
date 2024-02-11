export type Unsubscribe = () => void;

export type Subscribe<EventsDefinition> = <T extends keyof EventsDefinition>(
  type: Exclude<T, number | symbol>,
  handlerFn: (payload: EventsDefinition[T]) => void,
) => Unsubscribe;
export interface Subscribers<EventsDefinition> {
  addEventListener: Subscribe<EventsDefinition>;
}

export type Publish<
  EventsDefinition,
  T extends keyof EventsDefinition,
> = EventsDefinition[T] extends void
  ? (eventName: Exclude<T, number | symbol>, payload: undefined) => void
  : (
      eventName: Exclude<T, number | symbol>,
      payload: EventsDefinition[T],
    ) => void;

export interface Publishers<EventsDefinition> {
  dispatchEvent: Publish<EventsDefinition, keyof EventsDefinition>;
}

export type EventBusObject<E> = {
  eventBus: EventTarget;
  addEventListener: <T extends keyof E>(
    eventName: Exclude<T, number | symbol>,
    handlerFn: (payload: E[T]) => void,
  ) => Unsubscribe;
  dispatchEvent: <T extends keyof E>(
    eventName: Exclude<T, number | symbol>,
    payload?: E[T],
  ) => void;
};
