import type {
  CreateSafeEventBus,
  Subscribers,
  Unsubscribe,
} from '../types/safe-event-bus.types';

function isCustomEvent(event: Event): event is CustomEvent {
  return 'detail' in event;
}

export abstract class SafeEventBus<EventsDefinition>
  implements Subscribers<EventsDefinition>
{
  private readonly eventBus = new EventTarget();
  protected publish<T extends keyof EventsDefinition>(
    eventName: Exclude<T, number | symbol>,
    payload?: EventsDefinition[T],
  ): void {
    const event = payload
      ? new CustomEvent(eventName, { detail: payload })
      : new CustomEvent(eventName);
    this.eventBus.dispatchEvent(event);
  }

  private isCustomEvent(event: Event): event is CustomEvent {
    return 'detail' in event;
  }

  public subscribe<T extends keyof EventsDefinition>(
    eventName: Exclude<T, number | symbol>,
    handlerFn: (payload: EventsDefinition[T]) => void,
  ): Unsubscribe {
    const eventHandler = (event: Event) => {
      if (this.isCustomEvent(event)) {
        const eventPayload: EventsDefinition[T] = event.detail;
        handlerFn(eventPayload);
      }
    };
    this.eventBus.addEventListener(eventName, eventHandler);
    return () => {
      this.eventBus.removeEventListener(eventName, eventHandler);
    };
  }
}

export function createSafeEventBus<EventsDefinition>(
  eventBus = new EventTarget(),
): CreateSafeEventBus<EventsDefinition> {
  const publish =
    (eventBus: EventTarget) =>
    <T extends keyof EventsDefinition>(
      eventName: Exclude<T, number | symbol>,
      payload?: EventsDefinition[T],
    ): void => {
      const event = payload
        ? new CustomEvent(eventName, { detail: payload })
        : new CustomEvent(eventName);
      eventBus.dispatchEvent(event);
    };

  const subscribe =
    (eventBus: EventTarget) =>
    <T extends keyof EventsDefinition>(
      eventName: Exclude<T, number | symbol>,
      handlerFn: (payload: EventsDefinition[T]) => void,
    ): Unsubscribe => {
      const eventHandler = (event: Event) => {
        if (isCustomEvent(event)) {
          const eventPayload: EventsDefinition[T] = event.detail;
          handlerFn(eventPayload);
        }
      };
      eventBus.addEventListener(eventName, eventHandler);
      return () => {
        eventBus.removeEventListener(eventName, eventHandler);
      };
    };

  return {
    eventBus,
    publish: publish(eventBus),
    subscribe: subscribe(eventBus),
  };
}
