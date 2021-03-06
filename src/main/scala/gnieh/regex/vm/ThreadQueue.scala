/*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package gnieh.regex
package vm

import scala.collection.immutable.Queue

/** An immutable queue that maintains a hashset of the contained thread's pc.
 *  to allow for quick existence check.
 *
 *  @author Lucas Satabin
 */
class ThreadQueue(threads: Queue[RThread], pcs: Set[Int]) {

  def enqueue(t: RThread): ThreadQueue =
    new ThreadQueue(threads.enqueue(t), pcs + t.pc)

  def dequeue: (RThread, ThreadQueue) = {
    val (thread, rest) = threads.dequeue
    (thread, new ThreadQueue(rest, pcs - thread.pc))
  }

  def isEmpty: Boolean =
    threads.isEmpty

  def nonEmpty: Boolean =
    threads.nonEmpty

  def contains(t: RThread): Boolean =
    pcs.contains(t.pc)

}

object ThreadQueue {

  def apply(): ThreadQueue =
    new ThreadQueue(Queue(), Set())

}

